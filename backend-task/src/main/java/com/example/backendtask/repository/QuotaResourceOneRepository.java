package com.example.backendtask.repository;

import com.example.backendtask.entity.QuotaResourceOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for QuotaResourceOne entities.
 */
@Repository
public interface QuotaResourceOneRepository extends JpaRepository<QuotaResourceOne, Long> {

    // Find the quota resource by the associated user ID
    Optional<QuotaResourceOne> findByUserId(Long userId);

    // Find the quota resource by its blocking threshold
    Optional<QuotaResourceOne> findByBlockingThreshold(Integer blockingThreshold);

    // Custom query to check if the user has exceeded the blocking threshold for resource one
    boolean existsByUserIdAndRequestsMadeGreaterThan(Long userId, Integer requestsMade);

    // Optionally, find quota resource by UserId and Blocking-Threshold combination
    Optional<QuotaResourceOne> findByUserIdAndBlockingThreshold(Long userId, Integer blockingThreshold);
}
