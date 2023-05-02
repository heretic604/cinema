package com.heretic.main.controller;

import com.heretic.main.exception.IncorrectEmailException;
import com.heretic.main.exception.IncorrectUsernameException;
import com.heretic.main.exception.UsernameNotAvailableException;
import com.heretic.main.model.user.User;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;

/**
 * Provides menu before logging
 */
public interface UserController {

    /**
     * Start menu
     */
    void startMenu();

    /**
     * Prints start menu
     */
    void printStartMenu();

    /**
     * Functionality of start menu
     */
    void switchStartMenu();

    /**
     * Checks password
     */
    void checkPassword(User user);

    /**
     * Create new account
     */
    User register();

    /**
     * Entering name for account
     */
    String enterName() throws IncorrectUsernameException, UsernameNotAvailableException;

    /**
     * Entering password for account
     */
    String enterPassword();

    /**
     * Entering email for account
     */
    String enterEmail() throws IncorrectEmailException;

    /**
     * Entering birthday for account
     */
    LocalDate enterBirthday() throws InputMismatchException, DateTimeException;

    /**
     * Completes user registration
     */
    User assembleUser(String username, String password, String email, LocalDate birthday);

    /**
     * Prints greetings
     */
    void printGreetings();

}
