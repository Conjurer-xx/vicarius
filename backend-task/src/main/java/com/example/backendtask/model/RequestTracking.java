package com.example.backendtask.model;

import jakarta.persistence.*;

/**
 * Entity to track API usage for users, ensuring quota limits are respected.
 */
@Entity
@Table(name = "request_tracking")
public class RequestTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // The user making the requests

    @Column(nullable = false)
    private int requestsMade; // Total requests made by the user

    @Column(nullable = false)
    private boolean blocked; // Indicates if the user is permanently blocked

    // Default constructor
    public RequestTracking() {}

    // Constructor
    public RequestTracking(Long userId, int requestsMade, boolean blocked) {
        this.userId = userId;
        this.requestsMade = requestsMade;
        this.blocked = blocked;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRequestsMade() {
        return requestsMade;
    }

    public void setRequestsMade(int requestsMade) {
        this.requestsMade = requestsMade;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "RequestTracking{" +
                "id=" + id +
                ", userId=" + userId +
                ", requestsMade=" + requestsMade +
                ", blocked=" + blocked +
                '}';
    }
}

