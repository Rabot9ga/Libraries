package ru.sbt.util.jdbclib;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.sbt.util.jdbclib.DatabaseInterface.DBRepository;
import ru.sbt.util.jdbclib.core.JDBCMethodsImpl;
import ru.sbt.util.jdbclib.dto.ColumnType;
import ru.sbt.util.jdbclib.dto.JDBCPojo;
import ru.sbt.util.jdbclib.dto.JDBCPojoFactory;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JDBCMethodsImplTest {

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void testInsertInTable() throws Exception {
        DBRepository dbRepository = mock(DBRepository.class);
        JDBCPojo jdbcPojo = JDBCPojoFactory.getPojo()
                .addColumn("id", ColumnType.SERIAL, null)
                .addColumn("name", ColumnType.TEXT, "Вася")
                .addColumn("column1", ColumnType.INTEGER, "1")
                .addColumn("column2", ColumnType.DOUBLE, "1.3")
                .addColumn("column3", ColumnType.BIGINTEGER, "1354");


        JDBCMethodsImpl jdbcMethodsImpl = new JDBCMethodsImpl(dbRepository, batchSender);
        jdbcMethodsImpl.insertInTable("JDBCTestTable", jdbcPojo);


    }

}