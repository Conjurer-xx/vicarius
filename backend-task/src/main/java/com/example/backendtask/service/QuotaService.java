package com.example.backendtask.service;

import com.example.backendtask.entity.QuotaResourceOne;
import com.example.backendtask.entity.QuotaResourceTwo;
import com.example.backendtask.repository.QuotaResourceOneRepository;
import com.example.backendtask.repository.QuotaResourceTwoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class to handle quota management for the two resources.
 */
@Service
public class QuotaService {

    private final QuotaResourceOneRepository quotaResourceOneRepository;
    private final QuotaResourceTwoRepository quotaResourceTwoRepository;

    @Autowired
    public QuotaService(QuotaResourceOneRepository quotaResourceOneRepository, QuotaResourceTwoRepository quotaResourceTwoRepository) {
        this.quotaResourceOneRepository = quotaResourceOneRepository;
        this.quotaResourceTwoRepository = quotaResourceTwoRepository;
    }

    // Method to consume a quota resource for Resource One
    public boolean consumeQuotaResourceOne(Long userId) {
        Optional<QuotaResourceOne> quota = quotaResourceOneRepository.findByUserId(userId);

        if (quota.isPresent()) {
            QuotaResourceOne quotaResource = quota.get();
            // Check if the user has exceeded the blocking threshold
            if (quotaResource.getRequestsMade() >= quotaResource.getBlockingThreshold()) {
                // The user has exceeded the threshold
                return false;
            } else {
                // Increase the user's requests made
                quotaResource.setRequestsMade(quotaResource.getRequestsMade() + 1);
                quotaResourceOneRepository.save(quotaResource);  // Save the updated quota
                return true;
            }
        }

        return false; // No quota found for the user
    }

    // Method to consume a quota resource for Resource Two
    public boolean consumeQuotaResourceTwo(Long userId) {
        Optional<QuotaResourceTwo> quota = quotaResourceTwoRepository.findByUserId(userId);

        if (quota.isPresent()) {
            QuotaResourceTwo quotaResource = quota.get();
            // Check if the user has exceeded the blocking threshold
            if (quotaResource.getRequestsMade() >= quotaResource.getBlockingThreshold()) {
                // The user has exceeded the threshold
                return false;
            } else {
                // Increase the user's requests made
                quotaResource.setRequestsMade(quotaResource.getRequestsMade() + 1);
                quotaResourceTwoRepository.save(quotaResource);  // Save the updated quota
                return true;
            }
        }

        return false; // No quota found for the user
    }

    // Method to check the status of a user's quota for Resource One
    public boolean checkQuotaResourceOneStatus(Long userId) {
        Optional<QuotaResourceOne> quota = quotaResourceOneRepository.findByUserId(userId);
        return quota.isPresent() && quota.get().getRequestsMade() < quota.get().getBlockingThreshold();
    }

    // Method to check the status of a user's quota for Resource Two
    public boolean checkQuotaResourceTwoStatus(Long userId) {
        Optional<QuotaResourceTwo> quota = quotaResourceTwoRepository.findByUserId(userId);
        return quota.isPresent() && quota.get().getRequestsMade() < quota.get().getBlockingThreshold();
    }

    // Optional: You can also add methods to initialize or reset quotas for testing or other purposes.
    public void resetQuotaForResourceOne(Long userId) {
        Optional<QuotaResourceOne> quota = quotaResourceOneRepository.findByUserId(userId);
        quota.ifPresent(q -> {
            q.setRequestsMade(0);
            quotaResourceOneRepository.save(q);
        });
    }

    public void resetQuotaForResourceTwo(Long userId) {
        Optional<QuotaResourceTwo> quota = quotaResourceTwoRepository.findByUserId(userId);
        quota.ifPresent(q -> {
            q.setRequestsMade(0);
            quotaResourceTwoRepository.save(q);
        });
    }
}
