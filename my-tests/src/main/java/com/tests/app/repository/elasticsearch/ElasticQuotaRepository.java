package com.tests.app.repository.elasticsearch;

import com.tests.app.model.Quota;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticQuotaRepository extends ElasticsearchRepository<Quota, String> {
    Quota findByUserId(String userId);

}

