package ru.sbt.util.jdbclib.DatabaseInterface;

import lombok.extern.slf4j.Slf4j;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.sbt.util.jdbclib.dto.ColumnType;
import ru.sbt.util.jdbclib.dto.JDBCPojo;
import ru.sbt.util.jdbclib.util.MyCollectors;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        if (columnType.equals(ColumnType.SERIAL)) return "serial NOT NULL";
        if (columnType.equals(ColumnType.TEXT)) return "text";
        if (columnType.equals(ColumnType.BIGINTEGER)) return "bigint";
        if (columnType.equals(ColumnType.DOUBLE)) return "double precision";
        if (columnType.equals(ColumnType.INTEGER)) return "integer";
        return null;
    }

    @Override
    public void writeBatchInDB(String tableName, List<JDBCPojo> jdbcPojoList) {
        Map<String, ColumnType> schemaForCreate = getSchema(jdbcPojoList);
        createTable(tableName, schemaForCreate);

        String[] queries = StreamEx.of(jdbcPojoList)
                .map(jdbcPojo -> getQueryToInsert(tableName, jdbcPojo))
                .toArray(String.class);

        jdbcTemplate.batchUpdate(queries);
    }


    private String getQueryToInsert(String tableName, JDBCPojo jdbcPojo) {

        Map<String, String> mapColumnValue = jdbcPojo.getColumnValueWithoutSerialType();
        StringJoiner columnJoiner = new StringJoiner(", ", "(", ")");
        StringJoiner valueJoiner = new StringJoiner(", ", "(", ")");

        mapColumnValue.forEach((column, value) -> {
            columnJoiner.add("\"" + column + "\"");
            valueJoiner.add(value);
        });

        String format = String.format("INSERT INTO public.\"%s\" %s VALUES %s",
                tableName, columnJoiner.toString(), valueJoiner.toString());
        return format;
    }


    private String getQueryToInsert(String tableName, Map<String, ColumnType> schema) {
        StringBuilder builderQuery = new StringBuilder("INSERT INTO public.\"");
        builderQuery.append(tableName);
        builderQuery.append("\" ");

        StringJoiner stringJoiner = new StringJoiner(", ", "(", ")");
        schema.forEach((s, s2) -> stringJoiner.add("\"" + s + "\""));

        builderQuery.append(stringJoiner.toString());
        builderQuery.append(" VALUES ");

        StringJoiner valueJoiner = new StringJoiner(", ", "(", ")");
        for (int i = 0; i < schema.size(); i++) {
            valueJoiner.add("?");
        }
        builderQuery.append(valueJoiner.toString());
        builderQuery.append(";");
        return builderQuery.toString();
    }

    private void prepareStatement(JDBCPojo jdbcPojo, Map<String, ColumnType> schemaWithoutId) {

    }
}
