package ru.sbt.util.jdbclib.DatabaseInterface;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.sbt.util.jdbclib.dto.ColumnType;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

@Slf4j
public class DBRepositoryPostgresTest extends AbstractDBRepositoryTest {


    private DBRepositoryPostgres repositoryPostgres;

    @BeforeClass
    public void setUpClass() {
        jdbcTemplate = getStandardTemplate();
    }

    @BeforeMethod
    public void setUp() {
        repositoryPostgres = new DBRepositoryPostgres(jdbcTemplate);
        createTestTables();
    }

    @Test
    public void testGetAllTableNames() {
        List<String> tableNames = repositoryPostgres.getAllTableNames();
        assertEquals(tableNames, Arrays.asList("table1", "table2", "table3"), "tables names not exist");
    }

    @Test
    public void testCreateTable() {
        Map<String, ColumnType> nameTypesColums = new LinkedHashMap<>();
        nameTypesColums.put("id", ColumnType.SERIAL);
        nameTypesColums.put("name", ColumnType.TEXT);
        nameTypesColums.put("column1", ColumnType.INTEGER);
        nameTypesColums.put("column2", ColumnType.BIGINTEGER);
        nameTypesColums.put("column3", ColumnType.DOUBLE);

        repositoryPostgres.createTable("createTable", nameTypesColums);
        List<String> tableNames = repositoryPostgres.getAllTableNames();
        assertTrue(tableNames.contains("createTable"), "table_schema not contains createTable");
    }

    @Test
    public void testIsTableExists() {
        boolean table1 = repositoryPostgres.isTableExists("table1");
        log.info("table1: {}", table1);
        boolean table234 = repositoryPostgres.isTableExists("table234");
        log.info("table234: {}", table234);
        assertTrue(table1, "table1 should be created");
        assertFalse(table234, "table234 should be absent");
    }

    @Test
    public void testWriteBatchInDB() throws Exception {

        repositoryPostgres.writeBatchInDB("testTableJDBC", getListJDBCPojo(1000));
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("SELECT * FROM public.\"testTableJDBC\"");
        assertEquals(mapList.size(), 1000, "expected 1000 rows");



    }

    @AfterMethod
    public void tearDown() {
        deleteTestTables();
    }

    private void createTestTables() {
        createTestTable("table1");
        createTestTable("table2");
        createTestTable("table3");
    }

    private void deleteTestTables() {
        deleteTestTable("table1");
        deleteTestTable("table2");
        deleteTestTable("table3");
        deleteTestTable("createTable");
        deleteTestTable("testTableJDBC");
    }



}