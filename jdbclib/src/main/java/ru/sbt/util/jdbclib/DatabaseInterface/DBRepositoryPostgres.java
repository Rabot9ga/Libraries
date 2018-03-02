package ru.sbt.util.jdbclib.DatabaseInterface;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.sbt.util.jdbclib.dto.ColumnType;
import ru.sbt.util.jdbclib.dto.JDBCPojo;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class DBRepositoryPostgres implements DBRepository {

    private JdbcTemplate jdbcTemplate;

    public DBRepositoryPostgres(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> getAllTableNames() {
        return jdbcTemplate.queryForList("SELECT table_name FROM information_schema.tables WHERE TABLE_SCHEMA='public'").stream()
                .map(entry -> entry.get("TABLE_NAME").toString())
                .collect(Collectors.toList());
    }

    @Override
    public void createTable(String tableName, Map<String, ColumnType> nameTypesColumn) {
        if (isTableExists(tableName)) {
            log.info("tableName: {} is exists - skip step", tableName);
        } else {
            log.debug("create tableName: {}", tableName);
            log.trace("create nameTypesColumn: {}", nameTypesColumn);
            String query = getCreateTableQuery(tableName, nameTypesColumn);
            jdbcTemplate.execute(query);
        }
    }

    @Override
    public boolean isTableExists(String tableName) {
        List<String> tableNames = getAllTableNames();
        return tableNames.contains(tableName);
    }

    private String getCreateTableQuery(String tableName, Map<String, ColumnType> nameTypesColumn) {
        StringBuilder builder = new StringBuilder(String.format("CREATE TABLE public.\""));
        builder.append(tableName);
        builder.append("\" (");

        StringJoiner stringJoiner = new StringJoiner(", ");
        nameTypesColumn.forEach((s, s2) -> stringJoiner.add(String.format("%s %s", s, getColumnType(s2))));
        builder.append(stringJoiner.toString());
        builder.append(")");

        return builder.toString();
    }

    @Override
    public String getColumnType(ColumnType columnType) {
        if (columnType.equals(ColumnType.SERIAL)) return "serial";
        if (columnType.equals(ColumnType.TEXT)) return "text";
        if (columnType.equals(ColumnType.BIGINTEGER)) return "bigint";
        if (columnType.equals(ColumnType.DOUBLE)) return "double precision";
        if (columnType.equals(ColumnType.INTEGER)) return "integer";
        return null;
    }

    @Override
    public void writeBatchInDB(List<JDBCPojo> jdbcPojoList) {

    }

}
