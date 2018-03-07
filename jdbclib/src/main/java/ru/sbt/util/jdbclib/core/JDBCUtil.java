package ru.sbt.util.jdbclib.core;


import one.util.streamex.StreamEx;
import ru.sbt.util.batcher.BatchConsumer;
import ru.sbt.util.concurrent_util.ConcurrentUtil;
import ru.sbt.util.jdbclib.DatabaseInterface.DBRepository;
import ru.sbt.util.jdbclib.DatabaseInterface.DBRepositoryFactory;
import ru.sbt.util.jdbclib.dto.JDBCPojo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class JDBCUtil {

    static ConcurrentMap<String, BatchConsumer<JDBCPojo>> tableBatchSender = new ConcurrentHashMap<>();
    static ConcurrentMap<String, DBRepository> dbRepositoryMap = new ConcurrentHashMap<>();

    public static JDBCMethods getConnection(String url, String user, String password) {

        Supplier<DBRepository> repositorySupplier = () -> DBRepositoryFactory.getRepository(url, user, password);
        DBRepository dbRepository = ConcurrentUtil.putInMap(dbRepositoryMap, url, repositorySupplier);

        return new JDBCMethodsImpl(tableBatchSender, dbRepository);
    }

    public static JDBCMethods getConnection(String url, String user, String password,
                                            int batchSize, long timePeriod, TimeUnit timeUnit) {

        Supplier<DBRepository> repositorySupplier = () -> DBRepositoryFactory.getRepository(url, user, password);
        DBRepository dbRepository = ConcurrentUtil.putInMap(dbRepositoryMap, url, repositorySupplier);

        return new JDBCMethodsImpl(tableBatchSender, dbRepository, batchSize, timePeriod, timeUnit);
    }

    public static void flush() {
        StreamEx.ofValues(tableBatchSender).forEach(BatchConsumer::flush);
    }

}
