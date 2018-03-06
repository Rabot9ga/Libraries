package ru.sbt.util.jdbclib.core;

import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class JDBCUtilTest {

    @Test(invocationCount = 10, threadPoolSize = 5)
    public void testGet3Connection() {
        JDBCMethods jdbcMethods = JDBCUtil.getConnection("jdbc:postgresql://10.116.179.49:5432/Datapool", "tester", "123456");

        assertNotNull(jdbcMethods, null);
        assertEquals(JDBCUtil.dbRepositoryMap.size(), 1, "dbRepositoryMap.size() > 1");
        assertEquals(JDBCUtil.tableBatchSender.size(), 0, "tableBatchSender.size() != 0");
    }

    @Test(invocationCount = 10, threadPoolSize = 5)
    public void testGetConnectionWithBatch() {
        JDBCMethods jdbcMethods = JDBCUtil.getConnection("jdbc:postgresql://10.116.179.49:5432/Datapool", "tester", "123456",
                1000, 10, TimeUnit.SECONDS);

        assertNotNull(jdbcMethods, null);
        assertEquals(JDBCUtil.dbRepositoryMap.size(), 1, "dbRepositoryMap.size() > 1");
        assertEquals(JDBCUtil.tableBatchSender.size(), 0, "tableBatchSender.size() != 0");
    }


}