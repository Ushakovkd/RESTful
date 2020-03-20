package com.web.audio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.web.audio")
@PropertySource("classpath:db.properties")
public class DBConfig {

    @Autowired
    Environment environment;

    private static final String DB_DRIVER = "db.driver";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty(DB_URL));
        driverManagerDataSource.setUsername(environment.getProperty(DB_USER));
        driverManagerDataSource.setPassword(environment.getProperty(DB_PASSWORD));
        driverManagerDataSource.setDriverClassName(environment.getProperty(DB_DRIVER));
        return driverManagerDataSource;
    }
}
