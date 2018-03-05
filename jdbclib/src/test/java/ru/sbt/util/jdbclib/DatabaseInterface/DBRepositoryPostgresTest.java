package ru.sbt.util.jdbclib.DatabaseInterface;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.*;
import ru.sbt.util.jdbclib.dto.ColumnType;

import java.util.*;

import static org.testng.Assert.*;

@Slf4j
public class DBRepositoryPostgresTest {

    JdbcTemplate jdbcTemplate;
    private DBRepositoryPostgres repositoryPostgres;

    @BeforeClass
    public void setUpClass() throws Exception {
        jdbcTemplate = getStandardTemplate();
    }

    @BeforeMethod
    public void setUp() throws Exception {
        repositoryPostgres = new DBRepositoryPostgres(jdbcTemplate);
        createTestTables();
}

    @Test
    public void testGetAllTableNames() {
        List<String> tableNames = repositoryPostgres.getAllTableNames();
        assertEquals(tableNames, Arrays.asList("table1", "table2", "table3"), "tables names not exist");
    }

    @Test
    public void testCreateTable() throws Exception {
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
    public void testIsTableExists() throws Exception {
        boolean table1 = repositoryPostgres.isTableExists("table1");
        log.info("table1: {}", table1);
        boolean table234 = repositoryPostgres.isTableExists("table234");
        log.info("table234: {}", table234);
        assertTrue(table1, "table1 should be created");
        assertFalse(table234, "table234 should be absent");
    }

    @AfterMethod
    public void tearDown() throws Exception {
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
    }

    private void createTestTable(String tableName){
        try {
            jdbcTemplate.execute("CREATE TABLE public.\"" + tableName + "\"(id serial, name text)");
        } catch (Exception ignored) {
        }
    }

    private void deleteTestTable(String tableName) {
        // FIXME: 01.03.2018 Переделать на транзакции
        try {
            jdbcTemplate.execute("DROP TABLE public.\"" + tableName + "\"");
        } catch (Exception ignored) {
        }
    }


    private JdbcTemplate getStandardTemplate() {
        return getTemplate("jdbc:postgresql://10.116.179.49:5432/ForIntegrationTest",
                "tester", "123456");
    }

    private JdbcTemplate getTemplate(String jdbcUrl, String userName, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);
        return new JdbcTemplate(dataSource);
    }

}