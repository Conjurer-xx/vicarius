package com.example.backendtask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // MySQL properties
    @Value("${spring.datasource.mysql.url}")
    private String mysqlUrl;
    @Value("${spring.datasource.mysql.username}")
    private String mysqlUsername;
    @Value("${spring.datasource.mysql.password}")
    private String mysqlPassword;
    @Value("${spring.datasource.mysql.driver-class-name}")
    private String mysqlDriverClassName;

    // NotARealDB (mock DB) properties
    @Value("${spring.datasource.mockdb.url}")
    private String mockDbUrl;
    @Value("${spring.datasource.mockdb.username}")
    private String mockDbUsername;
    @Value("${spring.datasource.mockdb.password}")
    private String mockDbPassword;
    @Value("${spring.datasource.mockdb.driver-class-name}")
    private String mockDbDriverClassName;

    @Bean
    public DataSource dataSource() {
        CustomRoutingDataSource dataSource = new CustomRoutingDataSource();

        // MySQL DataSource Configuration
        DriverManagerDataSource mysqlDataSource = new DriverManagerDataSource();
        mysqlDataSource.setDriverClassName(mysqlDriverClassName);
        mysqlDataSource.setUrl(mysqlUrl);
        mysqlDataSource.setUsername(mysqlUsername);
        mysqlDataSource.setPassword(mysqlPassword);

        // NotARealDB DataSource Configuration
        DriverManagerDataSource mockDbDataSource = new DriverManagerDataSource();
        mockDbDataSource.setDriverClassName(mockDbDriverClassName);
        mockDbDataSource.setUrl(mockDbUrl);
        mockDbDataSource.setUsername(mockDbUsername);
        mockDbDataSource.setPassword(mockDbPassword);

        // Map of the data sources
        java.util.Map<Object, Object> dataSourceMap = new java.util.HashMap<>();
        dataSourceMap.put("MYSQL", mysqlDataSource);
        dataSourceMap.put("MOCK_DB", mockDbDataSource);

        dataSource.setTargetDataSources(dataSourceMap);
        return dataSource;
    }
}
