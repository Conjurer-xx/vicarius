package com.example.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backendtask.model.RequestTracking;

import java.util.Optional;

/**
 * Repository for managing request tracking records.
 */
@Repository
public interface RequestTrackingRepository extends JpaRepository<RequestTracking, Long> {

    /**
     * Finds a tracking record for a specific user.
     *
     * @param userId the user ID
     * @return an optional containing the request tracking record
     */
    Optional<RequestTracking> findByUserId(Long userId);
}

