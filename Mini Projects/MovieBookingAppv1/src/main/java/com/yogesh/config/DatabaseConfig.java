package com.yogesh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        // Hardcoded connection details (adjust for security and environment)
        dataSource.setURL("jdbc:mysql://localhost:3306/movie_booking_db?useSSL=false&serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("your_password");
        return dataSource;
    }
}
