package ru.sbt.util.jdbclib.core;

import ru.sbt.util.jdbclib.dto.JDBCPojo;

public interface JDBCMethods {
    void insertInTable(String tableName, JDBCPojo jdbcPojo);
}
