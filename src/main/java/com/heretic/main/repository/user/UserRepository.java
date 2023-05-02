package com.heretic.main.repository.user;

import com.heretic.main.model.user.User;
import com.heretic.main.service.user.UserService;

import java.util.List;

/**
 * User database interaction
 */
public interface UserRepository {

    /**
     * Add new user to database
     */
    boolean create(User user);

    /**
     * Get all users from database
     */
    List<User> getAll(UserService userService);

    /**
     * Get user by id from database
     */
    User get(Long id, UserService userService);

    /**
     * Get user by username from database
     */
    User get(String username, UserService userService);

    /**
     * Update user's info
     */
    User update(User user, Long id);

    /**
     * Delete user from database
     */
    User delete(Long id, UserService userService);

}
