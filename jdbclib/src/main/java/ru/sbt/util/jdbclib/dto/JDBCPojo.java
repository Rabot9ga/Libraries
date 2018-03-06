package ru.sbt.util.jdbclib.dto;

import java.util.Map;
import java.util.function.Function;

public interface JDBCPojo {
    JDBCPojo addColumn(String name, ColumnType type, String value);

    JDBCPojo addColumnId(String name);

    Map<String, ColumnType> getSchema();

    Map<String, String> getSchema(Function<ColumnType, String> function);

    Map<String, String> getColumnValue();

    Map<String, String> getColumnValueWithoutSerialType();
}
