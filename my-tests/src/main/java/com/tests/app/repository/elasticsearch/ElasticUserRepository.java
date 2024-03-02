package com.tests.app.repository.elasticsearch;

import com.tests.app.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticUserRepository extends ElasticsearchRepository<User,String> {
    // Mocked methods for ElasticSearch operations
    User findByUserId(String userId);

}

