package com.example.backendtask.repository;

import com.example.backendtask.entity.QuotaResourceTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for QuotaResourceTwo entities.
 */
@Repository
public interface QuotaResourceTwoRepository extends JpaRepository<QuotaResourceTwo, Long> {


    // Find the quota resource by the associated user ID
    Optional<QuotaResourceTwo> findByUserId(Long userId);

    // Find the quota resource by its blocking threshold
    Optional<QuotaResourceTwo> findByBlockingThreshold(Integer blockingThreshold);

    // Custom query to check if the user has exceeded the blocking threshold for resource two
    boolean existsByUserIdAndRequestsMadeGreaterThan(Long userId, Integer requestsMade);

    // Optionally, find quota resource by UserId and Blocking-Threshold combination
    Optional<QuotaResourceTwo> findByUserIdAndBlockingThreshold(Long userId, Integer blockingThreshold);
}
