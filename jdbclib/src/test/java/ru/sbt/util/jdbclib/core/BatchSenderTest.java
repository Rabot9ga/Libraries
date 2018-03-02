package ru.sbt.util.jdbclib.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.sbt.util.jdbclib.DatabaseInterface.DBRepository;
import ru.sbt.util.jdbclib.dto.ColumnType;
import ru.sbt.util.jdbclib.dto.JDBCPojo;
import ru.sbt.util.jdbclib.dto.JDBCPojoFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.*;

public class BatchSenderTest {


    private DBRepository dbRepository;


    @BeforeMethod
    public void setUp() throws Exception {
        dbRepository = mock(DBRepository.class);
        System.out.println("dbRepository = " + dbRepository);
    }

    @Test
    public void testPutBatch() throws Exception {
        BatchSender batchSender = new BatchSender(100, 10, TimeUnit.SECONDS, dbRepository);
        List<JDBCPojo> jdbcPojoList = getJdbcPojos(100);

        jdbcPojoList.forEach(batchSender::put);

        verify(dbRepository).writeBatchInDB(jdbcPojoList);
    }

    @Test
    public void testPutSome() throws Exception {
        BatchSender batchSender = new BatchSender(100, 300, TimeUnit.MILLISECONDS, dbRepository);
        List<JDBCPojo> jdbcPojos = getJdbcPojos(10);

        jdbcPojos.forEach(batchSender::put);

        verify(dbRepository, after(400).only()).writeBatchInDB(jdbcPojos);
    }

    @Test
    public void testPutExtraBatch() throws Exception {
        List<JDBCPojo> jdbcPojos = getJdbcPojos(199);
        BatchSender batchSender = new BatchSender(100, 300, TimeUnit.MILLISECONDS, dbRepository);

        jdbcPojos.forEach(batchSender::put);

        verify(dbRepository, after(20)).writeBatchInDB(jdbcPojos.subList(0, 100));
        verify(dbRepository, after(400).atLeastOnce()).writeBatchInDB(jdbcPojos.subList(100, 199));
    }

    private List<JDBCPojo> getJdbcPojos(int num) {
        return IntStream.range(0, num)
                .mapToObj(value -> JDBCPojoFactory.getPojo().addColumn("id", ColumnType.INTEGER, String.valueOf(value)))
                .collect(toList());
    }
}