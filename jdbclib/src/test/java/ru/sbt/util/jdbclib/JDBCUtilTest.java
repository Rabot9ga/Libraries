package ru.sbt.util.jdbclib;

import org.testng.annotations.Test;
import ru.sbt.util.jdbclib.core.JDBCMethods;
import ru.sbt.util.jdbclib.core.JDBCUtil;

import static org.testng.Assert.*;

public class JDBCUtilTest {
    @Test
    public void testInit() throws Exception {
        JDBCMethods jdbcMethods = JDBCUtil.getConnection("jdbc:postgresql://10.116.179.49:5432/Datapool");
        assertNotEquals(jdbcMethods, null);

        String tableName = "JDBCTestTable";


//        JDBCStatus jdbcStatus = jdbcMethods.insertInTable(tableName, jdbcPojo);
//        assertEquals(jdbcStatus.getStatus(), Status.SUCCESS);


    }
}