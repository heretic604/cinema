package com.heretic.main.service.user;

import com.heretic.main.controller.UserController;
import com.heretic.main.controller.role.AdminController;
import com.heretic.main.controller.role.RoleController;
import com.heretic.main.exception.AccountBlockedException;
import com.heretic.main.exception.UserNotFoundException;
import com.heretic.main.model.ticket.Ticket;
import com.heretic.main.model.user.Role;
import com.heretic.main.model.user.Status;
import com.heretic.main.model.user.User;

import java.util.List;

/**
 * User's information processing service
 */
public interface UserService {

    /**
     * Add new user to database
     */
    boolean create(User person);

    /**
     * Get all users
     */
    List<User> getAll();

    /**
     * Get user by username
     */
    User get(String username);

    /**
     * Get user by id from database
     */
    User get(Long id);

    /**
     * Update user's information by ID
     */
    User update(User user, Long id);

    /**
     * Remove user from database
     */
    User delete(Long id);

    /**
     * Check validation login
     */
    User checkAccount(String name) throws UserNotFoundException;

    /**
     * Check for account blocking
     */
    void checkBlocking(User user) throws AccountBlockedException;

    /**
     * Convert string variable to user's status
     */
    Status checkStatus(String status);

    /**
     * Convert string variable to user's role
     */
    Role checkRole(String role);

    /**
     * Check if username is available
     */
    boolean checkUsername(String name);

    /**
     * Checks account information
     */
    User verification(String act, UserController userController);

    /**
     * Define user's role, and gives him a controller
     */
    RoleController defineRoleController(User user);

    /**
     * Checks email validation
     */
    boolean validateEmail(String email);

    /**
     * Checks email validation
     */
    boolean validateUsername(String username);

    /**
     * Changes user's role
     */
    boolean changeRole(User user, AdminController adminController);

    /**
     * Changes user's status
     */
    boolean changeStatus(User user, AdminController adminController);

    /**
     * Remove user
     */
    boolean removeUser(User user);

    /**
     * Check user's age during purchase ticket
     */
    boolean checkAge(Ticket ticket, User user);

    /**
     * Encrypts the password and saves it to the database
     */
    String encryptPassword(String password, String username);
}
