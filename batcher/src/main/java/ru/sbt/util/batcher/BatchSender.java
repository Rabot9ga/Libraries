package ru.sbt.util.batcher;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class BatchSender<T> {

    private BlockingQueue<T> queue;
    private int batchSize;
    private TimeUnit timeUnitBatch;
    private long timePeriod;
    private ScheduledExecutorService service;
    private Consumer<List<T>> consumer;


    public BatchSender(Consumer<List<T>> consumer) {
        this(2000, 1, TimeUnit.SECONDS, consumer);
    }

    public BatchSender(int batchSize, long timePeriod, TimeUnit timeUnitBatch, Consumer<List<T>> consumer) {
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

    private void writeInDB() {
        ArrayList<T> list = new ArrayList<>();
        queue.drainTo(list);

        consumer.accept(list);
    }


    public void put(T newElement) {
        putInQueue(newElement);

        if (queue.size() >= batchSize) {
            service.submit(this::writeInDB);
        }
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
