package com.tests.app.controllers;

import com.google.common.base.Stopwatch;
import com.tests.app.model.User;
import com.tests.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            final ResponseEntity<User> ok = ResponseEntity.ok(userService.createUser(user));
            log.debug("createUser took: {}", stopwatch.stop());
            return ok;
        } catch (Exception e) {
            log.error("Error creating user in {}", stopwatch.stop(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            final ResponseEntity<User> ok = ResponseEntity.ok(userService.getUser(userId));
            log.debug("getUser took: {}", stopwatch.stop());
            return ok;
        } catch (Exception e) {
            log.error("Error getting user in {}", stopwatch.stop(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            final ResponseEntity<User> ok = ResponseEntity.ok(userService.updateUser(userId, updatedUser));
            log.debug("updateUser took: {}", stopwatch.stop());
            return ok;
        } catch (Exception e) {
            log.error("Error updating user in {}", stopwatch.stop(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            userService.deleteUser(userId);
            log.debug("deleteUser took: {}", stopwatch.stop());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting user in {}", stopwatch.stop(), e);
            return ResponseEntity.badRequest().body("Error deleting user:"  + e.getMessage());
        }
    }

    @PostMapping("/consumeQuota/{userId}")
    public ResponseEntity<?> consumeQuota(@PathVariable String userId) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            userService.consumeQuota(userId);
            log.debug("consumeQuota took: {}", stopwatch.stop());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error consuming quota", e);
            return ResponseEntity.badRequest().body("Error consuming quota:"  + e.getMessage());
        }
    }
}

