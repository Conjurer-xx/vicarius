package com.tests.app.service;

import com.tests.app.model.Quota;
import com.tests.app.model.User;
import com.tests.app.repository.elasticsearch.ElasticQuotaRepository;
import com.tests.app.repository.elasticsearch.ElasticUserRepository;
import com.tests.app.repository.jpa.QuotaRepository;
import com.tests.app.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Service for User entity
 * Since the application uses both MySQL and Elasticsearch with no known DB sync, the service is responsible for managing both repositories and their entities (User and Quota)
 * the user service is responsible for creating, updating, deleting, and retrieving user records from both MySQL and Elasticsearch
 * the user service is also responsible for managing the quota for each user, which is stored in both MySQL and Elasticsearch
 * User entity is stored in both MySQL and Elasticsearch and the quota entity is stored in both MySQL and Elasticsearch as well to ensure that the quota is managed consistently across both databases
 */
@Service
public class UserService {

    /**
     * The start time of the day
     */
    public static final LocalTime DAY_START_TIME = LocalTime.of(9, 0);

    /**
     * The end time of the day
     */
    public static final LocalTime DAY_END_TIME = LocalTime.of(17, 0);

    /**
     * The maximum number of requests allowed per day
     */
    public static final int MAX_QUOTA_COUNT = 5;

    @Autowired
    private UserRepository userRepository; // MySQL

    @Autowired
    private ElasticUserRepository elasticUserRepository; // Elasticsearch

    @Autowired
    private QuotaRepository quotaRepository; // MySQL Quota Repository

    @Autowired
    private ElasticQuotaRepository elasticQuotaRepository; // Elasticsearch Quota Repository

    /**
     * Create a new user in both MySQL and Elasticsearch and create a quota record for the user in both MySQL and Elasticsearch
     * @param user the user to create
     * @return the created user
     */
    public User createUser(User user) {
        // Save in MySQL
        User savedUser = userRepository.save(user);

        // Mock save in Elasticsearch
        elasticUserRepository.save(user);

        // Create and save quota records for both MySQL and Elasticsearch
        Quota quota = new Quota();
        quota.setUserId(user.getUserId());
        quota.setRequestCount(0);
        quotaRepository.save(quota);
        elasticQuotaRepository.save(quota);

        return savedUser;
    }

    /**
     * Update a user in both MySQL and Elasticsearch and update the quota record for the user in both MySQL and Elasticsearch as well
     *
     * @param userId  the user id
     * @param updatedUser the updated user
     * @return the updated user
     */
    public User updateUser(String userId, User updatedUser) {
        checkAndUpdateQuota(userId);

        // Update user in MySQL
        User mysqlUser = userRepository.findByUserId(userId);
        if (mysqlUser != null) {
            userRepository.save(updatedUser);
        }

        // Mock update in Elasticsearch
        User elasticUser = elasticUserRepository.findByUserId(userId);
        if (elasticUser != null) {
            elasticUserRepository.save(updatedUser);
        }

        return updatedUser;
    }


    /**
     * Delete a user from both MySQL and Elasticsearch and delete the quota record for the user from both MySQL and Elasticsearch
     * @param userId the user id
     */
    public void deleteUser(String userId) {
        checkAndUpdateQuota(userId);


        User mysqlUser = userRepository.findByUserId(userId);
        if (mysqlUser != null) {
            userRepository.delete(mysqlUser);
        }

        User elasticUser = elasticUserRepository.findByUserId(userId);
        if (elasticUser != null) {
            elasticUserRepository.delete(elasticUser);
        }

        Quota mysqlQuota = quotaRepository.findByUserId(userId);
        if (mysqlQuota != null) {
            quotaRepository.delete(mysqlQuota);
        }

        Quota elasticQuota = elasticQuotaRepository.findByUserId(userId); // Assuming this method exists
        if (elasticQuota != null) {
            elasticQuotaRepository.delete(elasticQuota);
        }
    }


    /**
     * Consume the quota for a user in both MySQL and Elasticsearch
     * @param userId the user id
     */
    public void consumeQuota(String userId) {
        checkAndUpdateQuota(userId);
    }

    /**
     * Check and update the quota for a user in both MySQL and Elasticsearch
     * @param userId the user id
     */
    private void checkAndUpdateQuota(String userId) {
        Quota jpaQuota = quotaRepository.findByUserId(userId);
        Quota elasticQuota = elasticQuotaRepository.findByUserId(userId);
        if (jpaQuota == null || elasticQuota == null) {
            throw new RuntimeException("Quota record not found");
        }
        if (jpaQuota.getRequestCount() + elasticQuota.getRequestCount() >= MAX_QUOTA_COUNT) {
            throw new RuntimeException("Quota exceeded");
        }
        if (isDuringDay()) {
            jpaQuota.setRequestCount(jpaQuota.getRequestCount() + 1);
            quotaRepository.save(jpaQuota);
        } else {
            elasticQuota.setRequestCount(elasticQuota.getRequestCount() + 1);
            elasticQuotaRepository.save(elasticQuota);
        }
    }

    /**
     * Get a user from MySQL or Elasticsearch based on the time of day
     * @param userId the user id
     * @return the user from MySQL or Elasticsearch based on the time of day
     */
    public User getUser(String userId) {
        checkAndUpdateQuota(userId);
        return isDuringDay() ? userRepository.findByUserId(userId) : elasticUserRepository.findByUserId(userId);
    }

    /**
     * Check if the current time is during the day (9am - 5pm)
     * @return true if the current time is during the day, false otherwise
     */
    private boolean isDuringDay() {
        LocalTime now = LocalTime.now(ZoneId.of("UTC"));
        return now.isAfter(DAY_START_TIME) && now.isBefore(DAY_END_TIME);
    }
}
