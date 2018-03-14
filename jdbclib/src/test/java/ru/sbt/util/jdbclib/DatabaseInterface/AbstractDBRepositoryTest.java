package ru.sbt.util.jdbclib.DatabaseInterface;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.sbt.util.jdbclib.dto.ColumnType;
import ru.sbt.util.jdbclib.dto.JDBCPojo;
import ru.sbt.util.jdbclib.dto.JDBCPojoFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public abstract class AbstractDBRepositoryTest {

    JdbcTemplate jdbcTemplate = getStandardTemplate();

    protected void createTestTable(String tableName) {
        try {
            jdbcTemplate.execute("CREATE TABLE public.\"" + tableName + "\"(id serial, name text)");
        } catch (Exception ignored) {
        }
    }

    protected void deleteTestTable(String tableName) {
        // FIXME: 01.03.2018 Переделать на транзакции
        try {
            jdbcTemplate.execute("DROP TABLE public.\"" + tableName + "\"");
        } catch (Exception ignored) {
        }
    }

    protected List<JDBCPojo> getListJDBCPojo(int num) {
        return IntStream.range(0, num)
                .mapToObj(value -> getJdbcPojo())
                .collect(toList());
    }

    protected JDBCPojo getJdbcPojo() {
        return JDBCPojoFactory.getPojo()
                .addColumnId("id")
                .addColumn("name", ColumnType.TEXT, UUID.randomUUID().toString())
                .addColumn("Column1", ColumnType.INTEGER, String.valueOf(ThreadLocalRandom.current().nextInt()))
                .addColumn("Column2", ColumnType.DOUBLE, String.valueOf(ThreadLocalRandom.current().nextDouble()))
                .addColumn("column3", ColumnType.BIGINTEGER, String.valueOf(ThreadLocalRandom.current().nextLong()));
    }

    protected JdbcTemplate getStandardTemplate() {
        return getTemplate("jdbc:postgresql://10.116.179.49:5432/ForIntegrationTest",
                "tester", "123456");
    }

    protected JdbcTemplate getTemplate(String jdbcUrl, String userName, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);
        return new JdbcTemplate(dataSource);
    }
}
