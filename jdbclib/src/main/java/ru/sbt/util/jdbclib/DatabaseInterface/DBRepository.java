package ru.sbt.util.jdbclib.DatabaseInterface;

import ru.sbt.util.jdbclib.dto.ColumnType;
import ru.sbt.util.jdbclib.dto.JDBCPojo;

import java.util.List;
import java.util.Map;

public interface DBRepository {
    List<String> getAllTableNames();

    void createTable(String tableName, Map<String, ColumnType> nameTypesColumn);

    boolean isTableExists(String tableName);

    String getColumnType(ColumnType columnType);

    void writeBatchInDB(List<JDBCPojo> jdbcPojoList);
}
