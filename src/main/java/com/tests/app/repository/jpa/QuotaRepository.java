package com.tests.app.repository.jpa;

import com.tests.app.model.Quota;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Quota entity
 */
public interface QuotaRepository extends JpaRepository<Quota, Long> {

    /**
     * Find Quota by userId
     * @param userId user id
     * @return Quota entity
     *
     * @see Quota
     */
    Quota findByUserId(String userId);
}
