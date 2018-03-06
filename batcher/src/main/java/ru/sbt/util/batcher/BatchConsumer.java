package ru.sbt.util.batcher;

import lombok.ToString;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

@ToString
public class BatchConsumer<T> {

    private BlockingQueue<T> queue;
    private int batchSize;
    private TimeUnit timeUnitBatch;
    private long timePeriod;
    private ScheduledExecutorService service;
    private Consumer<List<T>> consumer;


    public BatchConsumer(Consumer<List<T>> consumer) {
        this(consumer, 2000, 1, TimeUnit.SECONDS);
    }

    public BatchConsumer(Consumer<List<T>> consumer, int batchSize, long timePeriod, TimeUnit timeUnitBatch) {
        this.batchSize = batchSize;
        this.timeUnitBatch = timeUnitBatch;
        this.timePeriod = timePeriod;
        this.consumer = consumer;
        this.queue = new ArrayBlockingQueue<>(batchSize);

        BasicThreadFactory threadFactory = new BasicThreadFactory.Builder()
                .namingPattern("batch-sender")
                .daemon(true)
                .build();
        service = Executors.newSingleThreadScheduledExecutor(threadFactory);
        service.scheduleAtFixedRate(this::writeInDB, timePeriod, timePeriod, timeUnitBatch);
    }

    public void put(T newElement) {
        putInQueue(newElement);

        if (queue.size() >= batchSize) {
            service.submit(this::writeInDB);
        }
    }

    private void writeInDB() {
        ArrayList<T> list = new ArrayList<>();
        queue.drainTo(list);

        consumer.accept(list);
    }


    private void putInQueue(T newElement) {
        try {
            queue.put(newElement);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void flush() {
        writeInDB();
    }
}
