package ru.job4j.cinema.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(@Value("${jdbc.driver}") String driver,
                               @Value("${jdbc.url}") String url,
                               @Value("${jdbc.username}") String username,
                               @Value("${jdbc.password}") String password) {
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(driver);
        pool.setUrl(url);
        pool.setUsername(username);
        pool.setPassword(password);
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }
}
