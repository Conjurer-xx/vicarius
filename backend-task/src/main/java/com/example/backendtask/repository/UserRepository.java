package com.example.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backendtask.model.User;

import java.util.Optional;

/**
 * Repository for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
}
