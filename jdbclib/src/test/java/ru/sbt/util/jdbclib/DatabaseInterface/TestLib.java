package ru.sbt.util.jdbclib.DatabaseInterface;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.sbt.util.jdbclib.core.JDBCUtil;
import ru.sbt.util.jdbclib.dto.JDBCPojo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class TestLib extends AbstractDBRepositoryTest {

    private String tableName = "testJDBCTable";

    @BeforeMethod
    public void setUp() throws Exception {
        deleteTestTable(tableName);
    }

    @Test
    public void concurrentTestLibIT() throws Exception {

        int iterations = 10_000;


        CountDownLatch countDownLatch = new CountDownLatch(iterations);

        Runnable runnable = () -> {
            JDBCPojo jdbcPojo = getJdbcPojo();
            JDBCUtil.getConnection("jdbc:postgresql://10.116.179.49:5432/ForIntegrationTest", "tester", "123456")
                    .insertInTable(tableName, jdbcPojo);
            countDownLatch.countDown();
        };


        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < iterations; i++) {
            service.submit(runnable);
        }
        countDownLatch.await();
        JDBCUtil.flush();

        String sql = String.format("SELECT * FROM public.\"%s\"", tableName);
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        assertEquals(mapList.size(), iterations, "expected "+ iterations + " rows");
    }


    @Test
    public void testLibIT() throws Exception {
        int iterations = 10_000;

        for (int i = 0; i < iterations; i++) {
            JDBCPojo jdbcPojo = getJdbcPojo();
            JDBCUtil.getConnection("jdbc:postgresql://10.116.179.49:5432/ForIntegrationTest", "tester", "123456")
                    .insertInTable(tableName, jdbcPojo);
        }
        JDBCUtil.flush();

        String sql = String.format("SELECT * FROM public.\"%s\"", tableName);
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        assertEquals(mapList.size(), iterations, "expected "+ iterations + " rows");
    }

    @Test
    public void testLibWithCustomBatchIT() throws Exception {
        int iterations = 10_000;

        for (int i = 0; i < iterations; i++) {
            JDBCPojo jdbcPojo = getJdbcPojo();
            JDBCUtil.getConnection("jdbc:postgresql://10.116.179.49:5432/ForIntegrationTest", "tester", "123456",
                    3000, 2, TimeUnit.SECONDS)
                    .insertInTable(tableName, jdbcPojo);
        }
        JDBCUtil.flush();
        TimeUnit.SECONDS.sleep(1);

        String sql = String.format("SELECT * FROM public.\"%s\"", tableName);
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        assertEquals(mapList.size(), iterations, "expected " + iterations + " rows");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        deleteTestTable(tableName);
    }
}
