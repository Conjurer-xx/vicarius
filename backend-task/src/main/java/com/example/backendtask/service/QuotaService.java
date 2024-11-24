package com.example.backendtask.service;

/**
 * Service interface for managing quotas for API usage.
 */
public interface QuotaService {

    /**
     * Consumes a request for QuotaResourceOne.
     *
     * @param userId the ID of the user consuming the resource
     * @return 
     */
    void consumeQuotaResourceOne(Long userId);

    /**
     * Consumes a request for QuotaResourceTwo.
     *
     * @param userId the ID of the user consuming the resource
     * @return 
     */
    void consumeQuotaResourceTwo(Long userId);

    /**
     * Gets the status of QuotaResourceOne for a user.
     *
     * @param userId the ID of the user
     * @return true if the user is allowed to consume more of this resource; false otherwise
     */
    boolean getQuotaResourceOneStatus(Long userId);

    /**
     * Gets the status of QuotaResourceTwo for a user.
     *
     * @param userId the ID of the user
     * @return true if the user is allowed to consume more of this resource; false otherwise
     */
    boolean getQuotaResourceTwoStatus(Long userId);
}
