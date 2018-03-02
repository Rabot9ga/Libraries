package ru.sbt.util.jdbclib.dto;

import java.util.Map;
import java.util.function.Function;

public interface JDBCPojo {
    JDBCPojo addColumn(String name, ColumnType type, String value);

    Map<String, ColumnType> getSchema();

    Map<String, String> getData();

    Map<String, String> getData(Function<ColumnType, String> function);
}
