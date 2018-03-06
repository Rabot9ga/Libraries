package ru.sbt.util.jdbclib.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.sbt.util.batcher.BatchConsumer;
import ru.sbt.util.jdbclib.DatabaseInterface.DBRepository;
import ru.sbt.util.jdbclib.dto.ColumnType;
import ru.sbt.util.jdbclib.dto.JDBCPojo;
import ru.sbt.util.jdbclib.dto.JDBCPojoFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class JDBCMethodsImplTest {


    @BeforeMethod
    public void setUp() {

    }

    @Test
    public void testInsertInTable() {
        List<JDBCPojo> listJDBCPojo = getListJDBCPojo(3000);
        List<JDBCPojo> listJDBCPojo2 = getListJDBCPojo(3000);

        ConcurrentMap<String, BatchConsumer<JDBCPojo>> map = new ConcurrentHashMap<>();
        JDBCMethodsImpl jdbcMethodsImpl = new JDBCMethodsImpl(map, mock(DBRepository.class));

        listJDBCPojo.forEach(jdbcPojo -> jdbcMethodsImpl.insertInTable("testJDBCPojo", jdbcPojo));
        listJDBCPojo2.forEach(jdbcPojo -> jdbcMethodsImpl.insertInTable("testJDBCPojo2", jdbcPojo));

        assertEquals(map.size(), 2);
    }


    private List<JDBCPojo> getListJDBCPojo(int num) {
        return IntStream.rangeClosed(0, num)
                .mapToObj(value -> getJdbcPojo())
                .collect(toList());
    }

    private JDBCPojo getJdbcPojo() {
        return JDBCPojoFactory.getPojo()
                .addColumnId("id")
                .addColumn("name", ColumnType.TEXT, UUID.randomUUID().toString())
                .addColumn("column1", ColumnType.INTEGER, String.valueOf(ThreadLocalRandom.current().nextInt()))
                .addColumn("column2", ColumnType.DOUBLE, String.valueOf(ThreadLocalRandom.current().nextDouble()))
                .addColumn("column3", ColumnType.BIGINTEGER, String.valueOf(ThreadLocalRandom.current().nextLong()));
    }


}