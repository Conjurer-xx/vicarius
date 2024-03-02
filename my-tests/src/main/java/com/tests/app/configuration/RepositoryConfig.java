package com.tests.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.tests.app.repository.jpa")
@EnableElasticsearchRepositories(basePackages = "com.tests.app.repository.elasticsearch")
public class RepositoryConfig {
    // Configuration code here
}
