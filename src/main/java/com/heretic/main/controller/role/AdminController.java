package com.heretic.main.controller.role;

import com.heretic.main.controller.UserController;
import com.heretic.main.exception.MovieAlreadyExistException;
import com.heretic.main.model.movie.Movie;
import com.heretic.main.model.user.Role;
import com.heretic.main.model.user.Status;
import com.heretic.main.model.user.User;
import com.heretic.main.service.movie.MovieService;
import com.heretic.main.service.session.SessionService;
import com.heretic.main.service.ticket.TicketService;
import com.heretic.main.service.user.UserService;
import com.heretic.main.util.Values;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminController extends ManagerController {

    @Override
    public void printMainMenu(User user) {
        super.printMainMenu(user);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.FOUR_STR, Values.VIEW_MOVIE_LIBRARY);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.FIVE_STR, Values.VIEW_USERS);
    }

    @Override
    public void switchMainMenu(User user) {
        switch (scan.nextLine()) {
            case Values.ONE_STR -> accountMenu(user);
            case Values.TWO_STR -> ticketMenu(user);
            case Values.THREE_STR -> sessionsMenu(user);
            case Values.FOUR_STR -> movieMenu(user);
            case Values.FIVE_STR -> usersMenu(user);
            default -> userController.startMenu();
        }
    }

    public Role chooseRole() {

        System.out.println(Values.CHOOSE_ROLE);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Role.VISITOR.getTitle());
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Role.MANAGER.getTitle());
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.THREE_STR, Role.ADMIN.getTitle());

        return switch (scan.nextLine()) {
            case Values.TWO_STR -> Role.MANAGER;
            case Values.THREE_STR -> Role.ADMIN;
            default -> Role.VISITOR;
        };
    }

    public Status chooseStatus() {

        System.out.println(Values.CHOOSE_ROLE);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Status.ACTIVE.getTitle());
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Status.BLOCKED.getTitle());

        return scan.nextLine().equals(Values.ONE_STR) ? Status.ACTIVE : Status.BLOCKED;
    }

    private void movieMenu(User user) {
        printMovieMenu();
        switchMovieMenu(user);
    }

    private void printMovieMenu() {
        System.out.println(Values.ADMIN_MENU_HEAD);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.VIEW_MOVIES);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Values.ADD_MOVIE);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.THREE_STR, Values.REMOVE_MOVIE);
    }

    private void switchMovieMenu(User user) {
        switch (scan.nextLine()) {
            case Values.ONE_STR -> showAllMovies(user);
            case Values.TWO_STR -> addMovie(user);
            case Values.THREE_STR -> removeMovie(user);
            default -> mainMenu(user);
        }
    }

    private void addMovie(User user) {

        String name = createName();
        double IMDb = inputIMDb();
        int ageLimit = inputAgeLimit();
        String description = inputDescription();

        Movie movie = new Movie(name, IMDb, ageLimit, description);

        try {
            if (movieService.create(movie)) {
                System.out.println(Values.MOVIE_ADDED);
                log.info(Values.LOG_MOVIE_ADDING, user.getRole().getTitle(), user.getUsername(), movie.getName());
            } else System.out.println(Values.ADDING_FAILED);
        } catch (MovieAlreadyExistException e) {
            System.out.println(e.getMessage());
        }

        movieMenu(user);
    }

    private String createName() {
        System.out.println(Values.ENTER_MOVIE_NAME);
        return scan.nextLine();
    }

    private double inputIMDb() {

        System.out.println(Values.ENTER_IMDb);

        try {
            return Double.parseDouble(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return Values.ZERO;
    }

    private int inputAgeLimit() {

        System.out.println(Values.ENTER_AGE_LIMIT);

        try {
            return Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return Values.ZERO;
    }

    private String inputDescription() {
        System.out.println(Values.ENTER_DESCRIPTION);
        return scan.nextLine();
    }

    private void removeMovie(User user) {

        Movie movie = chooseMovie();

        if (movie != null) {
            movieService.delete(movie.getId());
            System.out.println(Values.MOVIE_REMOVED);
            log.info(Values.LOG_MOVIE_REMOVING, user.getRole().getTitle(), user.getUsername(), movie.getName());
        } else System.out.println(Values.DELETION_FILED);

        movieMenu(user);
    }

    private void showAllMovies(User user) {
        printListMovies(movieService.getAll());
        movieMenu(user);
    }

    private void usersMenu(User user) {
        printUsersMenu();
        switchUsersMenu(user);
    }

    private void printUsersMenu() {
        System.out.println(Values.ADMIN_MENU_HEAD);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.CHANGE_ROLE);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Values.CHANGE_STATUS);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.THREE_STR, Values.REMOVE_USER);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.FOUR_STR, Values.SHOW_USERS);
    }

    private void switchUsersMenu(User user) {
        switch (scan.nextLine()) {
            case Values.ONE_STR -> changeRole(user);
            case Values.TWO_STR -> changeStatus(user);
            case Values.THREE_STR -> removeUser(user);
            case Values.FOUR_STR -> showAccounts(user);
            default -> mainMenu(user);
        }
    }

    private void showAccounts(User user) {
        userService.getAll().forEach(System.out::println);
        usersMenu(user);
    }

    private void changeRole(User user) {

        User processedUser = chooseUser();

        if (userService.changeRole(processedUser, this)) {
            System.out.printf(Values.CHANGED + System.lineSeparator(), processedUser.getRole().getTitle(),
                    processedUser.getUsername());
            log.info(Values.LOG_CHANGING_ROLE, user.getRole().getTitle(), user.getUsername(),
                    processedUser.getUsername(), processedUser.getRole().getTitle());
        } else {
            System.out.println(Values.CHANGE_FAILED);
            log.warn(Values.LOG_CHANGE_ROLE_FAIL, user.getRole().getTitle(), user.getUsername(),
                    processedUser.getRole().getTitle(), processedUser.getUsername());
        }
        usersMenu(user);
    }

    private void changeStatus(User user) {

        User processedUser = chooseUser();

        if (userService.changeStatus(processedUser, this)) {
            System.out.printf(Values.CHANGED + System.lineSeparator(), processedUser.getStatus().getTitle(),
                    processedUser.getUsername());
            log.info(Values.LOG_CHANGING_STATUS, user.getRole().getTitle(), user.getUsername(), processedUser.getUsername(),
                    processedUser.getStatus().getTitle());
        } else {
            System.out.println(Values.CHANGE_FAILED);
            log.info(Values.LOG_CHANGE_STATUS_FAIL, user.getRole().getTitle(), user.getUsername(),
                    processedUser.getRole().getTitle(), processedUser.getUsername());
        }

        usersMenu(user);
    }

    private void removeUser(User user) {

        User processedUser = chooseUser();

        if (userService.removeUser(processedUser)) {
            System.out.printf(Values.USER_DELETED + System.lineSeparator(), processedUser.getUsername());
            log.info(Values.LOG_USER_REMOVING, user.getRole().getTitle(), user.getUsername(),
                    processedUser.getRole().getTitle(), processedUser.getUsername());
        } else {
            System.out.println(Values.DELETION_FILED);
            log.info(Values.LOG_USER_REMOVING_FAIL, user.getRole().getTitle(), user.getUsername());
        }

        usersMenu(user);
    }

    public AdminController(UserController userController, SessionService sessionService, TicketService ticketService, UserService userService, MovieService movieService) {
        super(userController, sessionService, ticketService, userService, movieService);
    }

}
