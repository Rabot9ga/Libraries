package ru.sbt.util.jdbclib.core;

import ru.sbt.util.jdbclib.DatabaseInterface.DBRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JDBCUtil {

    private static DBRepository dbRepository;
    private static ConcurrentMap<String, BatchSender> tableBatchSender = new ConcurrentHashMap<>();

    public static JDBCMethods getConnection(String s) {
        // TODO: 01.03.2018 Запилить многопоточность!
        return new JDBCMethodsImpl(dbRepository, batchSender);
    }
}
