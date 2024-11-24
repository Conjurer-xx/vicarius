package com.example.backendtask.service;

import com.example.backendtask.model.RequestTracking;
import com.example.backendtask.repository.RequestTrackingRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation of QuotaService for managing API quotas and user request tracking.
 */
@Service
public class QuotaServiceImpl implements QuotaService {

    private final RequestTrackingRepository requestTrackingRepository;

    // Injecting the repository via constructor injection
    public QuotaServiceImpl(RequestTrackingRepository requestTrackingRepository) {
        this.requestTrackingRepository = requestTrackingRepository;
    }

    @Override
    public void consumeQuotaResourceOne(Long userId) {
        handleQuotaConsumption(userId);
    }

    @Override
    public void consumeQuotaResourceTwo(Long userId) {
        handleQuotaConsumption(userId);
    }

    @Override
    public boolean getQuotaResourceOneStatus(Long userId) {
        return checkQuotaStatus(userId);
    }

    @Override
    public boolean getQuotaResourceTwoStatus(Long userId) {
        return checkQuotaStatus(userId);
    }

    /**
     * Handles quota consumption for a user.
     *
     * @param userId the ID of the user consuming the resource
     */
    private void handleQuotaConsumption(Long userId) {
        RequestTracking tracking = requestTrackingRepository
                .findByUserId(userId)
                .orElse(new RequestTracking(userId, 0, false));

        if (tracking.isBlocked()) {
            throw new RuntimeException("User is permanently blocked from consuming resources.");
        }

        int threshold = getThreshold();
        if (tracking.getRequestsMade() >= threshold) {
            tracking.setBlocked(true);
            requestTrackingRepository.save(tracking);
            throw new RuntimeException("User has exceeded the quota and is now permanently blocked.");
        }

        tracking.setRequestsMade(tracking.getRequestsMade() + 1);
        requestTrackingRepository.save(tracking);
    }

    /**
     * Checks if the user is allowed to consume more of a resource.
     *
     * @param userId the ID of the user
     * @return true if the user can consume more, false if the user is blocked or has exceeded the quota
     */
    private boolean checkQuotaStatus(Long userId) {
        RequestTracking tracking = requestTrackingRepository
                .findByUserId(userId)
                .orElse(new RequestTracking(userId, 0, false));

        if (tracking.isBlocked()) {
            return false;
        }

        int threshold = getThreshold();
        return tracking.getRequestsMade() < threshold;
    }

    /**
     * Retrieves the threshold for the number of requests allowed.
     *
     * @return the threshold value
     */
    private int getThreshold() {
        // Returning a fixed threshold for simplicity, e.g., 100 requests
        return 100;
    }
}
