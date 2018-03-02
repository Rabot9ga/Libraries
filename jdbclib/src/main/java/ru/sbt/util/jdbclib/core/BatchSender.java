package ru.sbt.util.jdbclib.core;

import ru.sbt.util.jdbclib.DatabaseInterface.DBRepository;
import ru.sbt.util.jdbclib.dto.JDBCPojo;
import ru.sbt.util.jdbclib.util.WorkerThreadFactory;

import java.util.ArrayList;
import java.util.concurrent.*;

public class BatchSender {

    private BlockingQueue<JDBCPojo> queue;
    private int batchSize;
    private TimeUnit timeUnitBatch;
    private long timePeriod;
    private ScheduledExecutorService service;
    private DBRepository dbRepository;


    public BatchSender(DBRepository dbRepository) {
        this(2000, 1, TimeUnit.SECONDS, dbRepository);
    }

    public BatchSender(int batchSize, long timePeriod, TimeUnit timeUnitBatch, DBRepository dbRepository) {
        this.batchSize = batchSize;
        this.timeUnitBatch = timeUnitBatch;
        this.timePeriod = timePeriod;
        this.dbRepository = dbRepository;
        this.queue = new ArrayBlockingQueue<>(batchSize);

        WorkerThreadFactory threadFactory = new WorkerThreadFactory("batch-sender");
        service = Executors.newSingleThreadScheduledExecutor(threadFactory);
        service.scheduleAtFixedRate(this::writeInDB, timePeriod, timePeriod, timeUnitBatch);
    }

    private void writeInDB() {
        ArrayList<JDBCPojo> list = new ArrayList<>();
        queue.drainTo(list);

        dbRepository.writeBatchInDB(list);
    }


    public void put(JDBCPojo jdbcPojo) {
        putInQueue(jdbcPojo);

        if (queue.size() >= batchSize) {
            service.submit(this::writeInDB);
        }
    }

    private void putInQueue(JDBCPojo jdbcPojo) {
        try {
            queue.put(jdbcPojo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
