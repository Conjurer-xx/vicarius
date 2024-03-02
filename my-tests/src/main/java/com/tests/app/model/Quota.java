package com.tests.app.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Quota entity class
 */
@Entity
@Data
public class Quota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;
    private int requestCount;
}

