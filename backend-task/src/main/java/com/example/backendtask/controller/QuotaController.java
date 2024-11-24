package com.example.backendtask.controller;

import com.example.backendtask.service.QuotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class QuotaController {

    private final QuotaService quotaService;

    public QuotaController(QuotaService quotaService) {
        this.quotaService = quotaService;
    }

    // Endpoint to get the status of QuotaResourceOne for a user
    @GetMapping("/{userId}/quota-resource-one-status")
    public ResponseEntity<Boolean> getQuotaResourceOneStatus(@PathVariable Long userId) {
        boolean status = quotaService.getQuotaResourceOneStatus(userId);
        return ResponseEntity.ok(status);
    }

    // Endpoint to get the status of QuotaResourceTwo for a user
    @GetMapping("/{userId}/quota-resource-two-status")
    public ResponseEntity<Boolean> getQuotaResourceTwoStatus(@PathVariable Long userId) {
        boolean status = quotaService.getQuotaResourceTwoStatus(userId);
        return ResponseEntity.ok(status);
    }

    // Endpoint to consume QuotaResourceOne for a user
    @PostMapping("/{userId}/consume-resource-one")
    public ResponseEntity<String> consumeQuotaResourceOne(@PathVariable Long userId) {
        try {
            quotaService.consumeQuotaResourceOne(userId);
            return ResponseEntity.ok("Quota consumed for resource one.");
        } catch (RuntimeException ex) {
            // If the user is blocked (i.e., exceeded the threshold), return a Forbidden status
            return ResponseEntity.status(403).body(ex.getMessage());
        }
    }

    // Endpoint to consume QuotaResourceTwo for a user
    @PostMapping("/{userId}/consume-resource-two")
    public ResponseEntity<String> consumeQuotaResourceTwo(@PathVariable Long userId) {
        try {
            quotaService.consumeQuotaResourceTwo(userId);
            return ResponseEntity.ok("Quota consumed for resource two.");
        } catch (RuntimeException ex) {
            // If the user is blocked (i.e., exceeded the threshold), return a Forbidden status
            return ResponseEntity.status(403).body(ex.getMessage());
        }
    }
}
