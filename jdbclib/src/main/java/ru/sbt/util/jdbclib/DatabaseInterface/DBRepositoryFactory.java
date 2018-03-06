package ru.sbt.util.jdbclib.DatabaseInterface;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DBRepositoryFactory {

    public static DBRepository getRepository(String url, String user, String password) {
        // TODO: 05.03.2018 Сделать для других БД
        return getPostgresRepository(url, user, password);
    }

    private static DBRepository getPostgresRepository(String url, String user, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);
        return new DBRepositoryPostgres(new JdbcTemplate(dataSource));
    }
}
