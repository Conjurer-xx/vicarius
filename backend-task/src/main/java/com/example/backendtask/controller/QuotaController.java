package com.example.backendtask.controller;

import com.example.backendtask.service.QuotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Quota management.
 */
@RestController
@RequestMapping("/api/quotas")
public class QuotaController {

    private final QuotaService quotaService;

    public QuotaController(QuotaService quotaService) {
        this.quotaService = quotaService;
    }

    @PostMapping("/resource-one/{userId}")
    public ResponseEntity<String> consumeResourceOne(@PathVariable Long userId) {
        boolean success = quotaService.consumeQuotaResourceOne(userId);
        return success
                ? ResponseEntity.ok("Quota consumed successfully.")
                : ResponseEntity.status(429).body("Quota exceeded.");
    }

    @PostMapping("/resource-two/{userId}")
    public ResponseEntity<String> consumeResourceTwo(@PathVariable Long userId) {
        boolean success = quotaService.consumeQuotaResourceTwo(userId);
        return success
                ? ResponseEntity.ok("Quota consumed successfully.")
                : ResponseEntity.status(429).body("Quota exceeded.");
    }

    @GetMapping("/resource-one/{userId}")
    public ResponseEntity<Boolean> getResourceOneStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(quotaService.checkQuotaResourceOneStatus(userId));
    }

    @GetMapping("/resource-two/{userId}")
    public ResponseEntity<Boolean> getResourceTwoStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(quotaService.checkQuotaResourceTwoStatus(userId));
    }
}

