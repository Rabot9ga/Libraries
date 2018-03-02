package ru.sbt.util.jdbclib.core;

import lombok.Data;
import ru.sbt.util.jdbclib.DatabaseInterface.DBRepository;
import ru.sbt.util.jdbclib.dto.JDBCPojo;

@Data
public class JDBCMethodsImpl implements JDBCMethods {

    private DBRepository dbRepository;
    private BatchSender batchSender;

    public JDBCMethodsImpl(DBRepository dbRepository, BatchSender batchSender) {
        this.dbRepository = dbRepository;
        this.batchSender = batchSender;
    }

    @Override
    public void insertInTable(String tableName, JDBCPojo jdbcPojo) {


    }
}
