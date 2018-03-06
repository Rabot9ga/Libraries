package ru.sbt.util.jdbclib.core;

import lombok.Data;
import ru.sbt.util.batcher.BatchConsumer;
import ru.sbt.util.concurrent_util.ConcurrentUtil;
import ru.sbt.util.jdbclib.DatabaseInterface.DBRepository;
import ru.sbt.util.jdbclib.dto.JDBCPojo;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Data
public class JDBCMethodsImpl implements JDBCMethods {

    private ConcurrentMap<String, BatchConsumer<JDBCPojo>> batchConsumerMap;
    private DBRepository dbRepository;
    private int batchSize;
    private long batchPeriod;
    private TimeUnit batchTimeUnit;


    public JDBCMethodsImpl(ConcurrentMap<String, BatchConsumer<JDBCPojo>> batchConsumerMap, DBRepository dbRepository) {
        this(batchConsumerMap, dbRepository, 2000, 1, TimeUnit.SECONDS);
    }

    public JDBCMethodsImpl(ConcurrentMap<String, BatchConsumer<JDBCPojo>> batchConsumerMap, DBRepository dbRepository,
                           int batchSize, long batchPeriod, TimeUnit batchTimeUnit) {

        this.batchConsumerMap = batchConsumerMap;
        this.dbRepository = dbRepository;
        this.batchSize = batchSize;
        this.batchPeriod = batchPeriod;
        this.batchTimeUnit = batchTimeUnit;
    }

    @Override
    public void insertInTable(String tableName, JDBCPojo jdbcPojo) {

        Supplier<BatchConsumer<JDBCPojo>> supplier =
                () -> new BatchConsumer<>(jdbcPojos -> dbRepository.writeBatchInDB(tableName, jdbcPojos),
                        batchSize, batchPeriod, batchTimeUnit);

        BatchConsumer<JDBCPojo> batchConsumer = ConcurrentUtil.putInMap(batchConsumerMap, tableName, supplier);
        batchConsumer.put(jdbcPojo);
    }


}
