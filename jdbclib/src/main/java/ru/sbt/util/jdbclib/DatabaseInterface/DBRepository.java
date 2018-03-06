package ru.sbt.util.jdbclib.DatabaseInterface;

import one.util.streamex.StreamEx;
import ru.sbt.util.jdbclib.dto.ColumnType;
import ru.sbt.util.jdbclib.dto.JDBCPojo;
import ru.sbt.util.jdbclib.util.MyCollectors;

import java.util.List;
import java.util.Map;

public interface DBRepository {
    List<String> getAllTableNames();

    void createTable(String tableName, Map<String, ColumnType> nameTypesColumn);

    boolean isTableExists(String tableName);

    String getColumnType(ColumnType columnType);

    void writeBatchInDB(String tableName, List<JDBCPojo> jdbcPojoList);

    default Map<String, ColumnType> getSchema(List<JDBCPojo> jdbcPojoList) {
        return StreamEx.of(jdbcPojoList)
                .flatMapToEntry(JDBCPojo::getSchema)
                .distinctKeys()
                .collect(MyCollectors.toLinkedMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

