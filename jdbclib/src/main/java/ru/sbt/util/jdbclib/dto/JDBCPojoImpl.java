package ru.sbt.util.jdbclib.dto;

import lombok.Data;
import ru.sbt.util.jdbclib.util.MoreCollectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Data
public class JDBCPojoImpl implements JDBCPojo {
    private List<Column> columnList = new ArrayList<>();

    @Override
    public JDBCPojo addColumn(String name, ColumnType type, String value) {
        columnList.add(new Column(name, type, value));
        return this;
    }

    @Override
    public Map<String, ColumnType> getSchema() {
        return columnList.stream().collect(MoreCollectors.toLinkedMap(o -> o.name, o -> o.type));
    }

    @Override
    public Map<String, String> getData() {
        return null;
    }

    @Override
    public Map<String, String> getData(Function<ColumnType, String> function) {

        return null;
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
