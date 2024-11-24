package com.example.backendtask.service;

import com.example.backendtask.model.User;
import com.example.backendtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling user-related operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user in the database.
     * @param user The user to be created.
     * @return The created user.
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user to retrieve.
     * @return The user with the specified ID, or null if not found.
     */
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    /**
     * Retrieves all users from the database.
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Updates an existing user in the database.
     * @param id The ID of the user to update.
     * @param user The user object with updated information.
     * @return The updated user, or null if the user was not found.
     */
    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id); // Ensure the ID stays the same
            return userRepository.save(user);
        }
        return null;
    }

    /**
     * Deletes a user from the database.
     * @param id The ID of the user to delete.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}