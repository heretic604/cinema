package com.heretic.main.controller;

import com.heretic.main.controller.role.RoleController;
import com.heretic.main.exception.*;
import com.heretic.main.model.user.User;
import com.heretic.main.service.user.UserService;
import com.heretic.main.util.Values;
import lombok.extern.slf4j.Slf4j;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class UserControllerImpl implements UserController {

    private final Scanner scan = new Scanner(System.in);
    private final UserService userService;

    @Override
    public void startMenu() {
        printGreetings();
        printStartMenu();
        switchStartMenu();
    }

    @Override
    public void printGreetings() {

        char[] chars = Values.GREETINGS.toCharArray();

        for (char c : chars) {

            System.out.print(c);

            try {
                Thread.sleep(Values.MILLIS_FOR_SLEEP);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(System.lineSeparator() + Values.STARS);

    }

    @Override
    public void checkPassword(User user) {

        boolean success;

        System.out.println(Values.ENTER_PASSWORD);

        do {
            String act = scan.nextLine();

            success = true;

            try {
                if (userService.encryptPassword(act, user.getUsername()).equals(user.getPassword())) {
                    System.out.println(Values.ACCESS_DENIED);
                } else throw new IncorrectPasswordException();
            } catch (IncorrectPasswordException e) {
                System.out.println(e.getMessage());
                success = false;
            }

        } while (!success);
    }

    @Override
    public void printStartMenu() {
        System.out.println(Values.ENTER_LOGIN);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.REGISTER_ACCOUNT);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.EXIT);
    }

    @Override
    public void switchStartMenu() {

        RoleController roleController;
        User user = null;

        do {

            String act = scan.nextLine();

            switch (act) {
                case Values.ZERO_STR -> System.exit(Values.ZERO);
                case Values.ONE_STR -> user = register();
                default -> user = userService.verification(act, this);
            }

        } while (user == null);

        roleController = userService.defineRoleController(user);
        roleController.mainMenu(user);
    }

    @Override
    public User register() {

        boolean success;
        String username = null;
        String password = null;
        String email = null;
        LocalDate birthday = null;

        do {
            try {
                success = true;
                username = enterName();
                password = userService.encryptPassword(enterPassword(), username);
                email = enterEmail();
                birthday = enterBirthday();
            } catch (IncorrectUsernameException | IncorrectEmailException | InputMismatchException |
                     DateTimeException | UsernameNotAvailableException e) {
                System.out.println(e.getMessage());
                log.info(Values.LOG_REGISTER_FAILED + e.getMessage());
                success = false;
            }
        } while (!success);

        return assembleUser(username, password, email, birthday);
    }

    @Override
    public User assembleUser(String username, String password, String email, LocalDate birthday) {

        User user = new User(username, password, email, birthday);

        userService.create(user);
        user = userService.get(user.getUsername());
        log.info(Values.LOG_REGISTER, user.getUsername());

        return user;
    }

    @Override
    public String enterName() throws IncorrectUsernameException, UsernameNotAvailableException {

        System.out.println(Values.ENTER_NAME);

        String name = scan.nextLine();

        if (!userService.validateUsername(name)) {
            throw new IncorrectUsernameException();
        } else if (!userService.checkUsername(name)) {
            throw new UsernameNotAvailableException();
        } else return name;
    }

    @Override
    public String enterPassword() {

        boolean success;
        String password;

        System.out.printf(Values.ENTER_NEW_PASSWORD + System.lineSeparator(), Values.PASSWORD_LENGTH);

        do {
            success = true;
            password = scan.nextLine();

            if (password.toCharArray().length < Values.PASSWORD_LENGTH) {
                System.out.println(Values.SHORT_PASSWORD);
                success = false;
            }

        } while (!success);

        return password;
    }

    @Override
    public String enterEmail() throws IncorrectEmailException {

        System.out.println(Values.ENTER_EMAIL);

        String email = scan.nextLine();

        if (userService.validateEmail(email)) {
            return email;
        } else throw new IncorrectEmailException();
    }

    @Override
    public LocalDate enterBirthday() throws InputMismatchException, DateTimeException {

        System.out.println(Values.ENTER_BIRTHDAY);

        do {

            try {

                int year = Integer.parseInt(scan.nextLine());
                int month = Integer.parseInt(scan.nextLine());
                int day = Integer.parseInt(scan.nextLine());
                return LocalDate.of(year, month, day);
                
            } catch (InputMismatchException | DateTimeException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

}
