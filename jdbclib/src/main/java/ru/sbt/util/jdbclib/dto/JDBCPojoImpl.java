package ru.sbt.util.jdbclib.dto;

import lombok.Data;
import one.util.streamex.StreamEx;
import ru.sbt.util.jdbclib.util.MyCollectors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static ru.sbt.util.jdbclib.util.MyCollectors.*;
import static ru.sbt.util.jdbclib.util.MyCollectors.toLinkedMap;

@Data
public class JDBCPojoImpl implements JDBCPojo {
    private List<Column> columnList = new ArrayList<>();

    public JDBCPojoImpl() {

    }

    @Override
    public JDBCPojo addColumn(String name, ColumnType type, String value) {
        columnList.add(new Column(name, type, value));
        return this;
    }

    @Override
    public JDBCPojo addColumnId(String name) {
        columnList.add(new Column(name, ColumnType.SERIAL, null));
        return this;
    }

    @Override
    public Map<String, ColumnType> getSchema() {
        return columnList.stream().collect(toLinkedMap(o -> o.name, o -> o.type));
    }

    @Override
    public Map<String, String> getSchema(Function<ColumnType, String> function) {
        return columnList.stream().collect(toLinkedMap(o -> o.name, o -> function.apply(o.type)));
    }

    @Override
    public Map<String, String> getColumnValue() {
        return StreamEx.of(columnList).toMap(column -> column.name, column -> column.value);
    }

    @Override
    public Map<String, String> getColumnValueWithoutSerialType() {
        return StreamEx.of(columnList)
                .filter(column -> column.type != ColumnType.SERIAL)
                .peek(this::columnValueStringReplace)
                .collect(toLinkedMap(column -> column.name, column -> column.value));
    }

    private Column columnValueStringReplace(Column column) {
        if (column.type == ColumnType.TEXT) {
            column.value = "\'" + column.value + "\'";
        }
        return column;
    }


    private class Column {
        String name;
        ColumnType type;
        String value;

        public Column(String name, ColumnType type, String value) {
            this.name = name;
            this.type = type;
            this.value = value;
        }
    }
}
