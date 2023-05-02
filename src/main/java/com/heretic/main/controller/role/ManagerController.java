package com.heretic.main.controller.role;

import com.heretic.main.controller.UserController;
import com.heretic.main.exception.NoMovieException;
import com.heretic.main.exception.NoTicketsException;
import com.heretic.main.exception.TimeNotAvailableException;
import com.heretic.main.model.movie.Movie;
import com.heretic.main.model.session.Session;
import com.heretic.main.model.ticket.Ticket;
import com.heretic.main.model.user.User;
import com.heretic.main.service.movie.MovieService;
import com.heretic.main.service.session.SessionService;
import com.heretic.main.service.ticket.TicketService;
import com.heretic.main.service.user.UserService;
import com.heretic.main.util.Values;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.util.InputMismatchException;
import java.util.List;

@Slf4j
public class ManagerController extends VisitorController {

    protected final MovieService movieService;

    @Override
    public void printTicketMenu(User user) {
        super.printTicketMenu(user);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Values.REVOKE_TICKET);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.THREE_STR, Values.ADD_TICKET);
    }

    @Override
    public void switchTicketMenu(User user) {
        switch (scan.nextLine()) {
            case Values.ONE_STR -> returnTicket(user);
            case Values.TWO_STR -> revokeTicket(user);
            case Values.THREE_STR -> addTicket(user);
            default -> mainMenu(user);
        }
    }

    @Override
    public void printSessionMenu() {
        super.printSessionMenu();
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Values.EDIT_SESSION);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.THREE_STR, Values.ADD_SESSION);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.FOUR_STR, Values.REMOVE_SESSION);
    }

    @Override
    public void switchSessionMenu(User user) {
        switch (scan.nextLine()) {
            case Values.ONE_STR -> sessionsShowMenu(user);
            case Values.TWO_STR -> editSession(user);
            case Values.THREE_STR -> addSession(user);
            case Values.FOUR_STR -> removeSession(user);
            default -> mainMenu(user);
        }
    }

    protected void editSession(User user) {
        Session session = chooseSession(sessionService.getAll());
        editSessionMenu(session, user);
    }

    protected void editSessionMenu(Session session, User user) {
        printSessionEditMenu(session);
        switchSessionEditMenu(session, user);
    }

    protected void printSessionEditMenu(Session session) {
        System.out.println(session);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.EDIT_TIME);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Values.EDIT_MOVIE);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.THREE_STR, Values.EDIT_PRICE);
    }

    protected void switchSessionEditMenu(Session session, User user) {
        switch (scan.nextLine()) {
            case Values.ONE_STR -> editTime(session, user);
            case Values.TWO_STR -> changeMovie(session, user);
            case Values.THREE_STR -> editPrice(session, user);
            default -> sessionsMenu(user);
        }
    }

    protected void editTime(Session session, User user) {

        try {
            session.setTime(chooseDataTime());
            sessionService.update(session, session.getId());
            System.out.println(Values.CHANGES_SAVED);
        } catch (TimeNotAvailableException e) {
            System.out.println(e.getMessage());
        }

        sessionsMenu(user);
    }

    protected LocalTime chooseTime() throws DateTimeException, InputMismatchException {
        printPeriods();
        return switch (scan.nextLine()) {
            case Values.ONE_STR -> LocalTime.of(Values.MORNING_HOUR, Values.ZERO);
            case Values.TWO_STR -> LocalTime.of(Values.AFTERNOON_HOUR, Values.ZERO);
            default -> LocalTime.of(Values.EVENING_HOUR, Values.ZERO);
        };
    }

    protected void printPeriods() {
        System.out.println(Values.ENTER_TIME);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.MORNING);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Values.AFTERNOON);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.THREE_STR, Values.EVENING);
    }

    protected void changeMovie(Session session, User user) {

        session.setMovie(chooseMovie());

        if (sessionService.update(session, session.getId()) != null) {
            System.out.println(Values.CHANGES_SAVED);
        }

        sessionsMenu(user);
    }

    protected Movie chooseMovie() {

        int act = Values.ZERO;
        List<Movie> movies = movieService.getAll();

        printChoosingMovieMenu();
        printListMovies(movies);

        try {
            act = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return executeChoosingMovie(act, movies);
    }

    protected void printChoosingMovieMenu() {
        System.out.println(Values.CHOOSE_MOVIE);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);
    }

    protected void printListMovies(List<Movie> movies) {
        for (int i = Values.ZERO; i < movies.size(); i++) {
            System.out.printf(Values.SAMPLE + System.lineSeparator(), (i + Values.ONE), movies.get(i));
        }
    }

    protected Movie executeChoosingMovie(int act, List<Movie> movies) {
        if (act <= Values.ZERO || act > movies.size()) {
            return null;
        } else return movies.get(act - Values.ONE);
    }

    protected void editPrice(Session session, User user) {

        System.out.println(Values.ENTER_PRICE);

        try {
            int price = Integer.parseInt(scan.nextLine());
            session.setPrice(price);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }

        if (sessionService.update(session, session.getId()) != null) {
            System.out.println(Values.CHANGES_SAVED);
        }

        sessionsMenu(user);
    }

    protected void addSession(User user) {

        try {
            LocalDateTime dateTime = chooseDataTime();

            Movie movie = chooseMovie();
            if (movie == null) throw new NoMovieException();

            int price = choosePrice();

            Session session = new Session(dateTime, movie, price);

            if (sessionService.create(session)) {
                session = sessionService.get(dateTime);
                ticketService.createTickets(session);
                System.out.println(Values.SESSION_CREATED);
            }

        } catch (TimeNotAvailableException | NoMovieException | DateTimeException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        sessionsMenu(user);
    }

    protected LocalDateTime chooseDataTime() throws TimeNotAvailableException, DateTimeException, NumberFormatException {

        LocalDate date = selectDate();
        LocalTime time = chooseTime();
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        if (!checkTime(dateTime)) {
            throw new TimeNotAvailableException();
        }

        return dateTime;
    }

    protected boolean checkTime(LocalDateTime dateTime) {

        for (Session session : sessionService.getAll()) {
            if (session.getTime().equals(dateTime)) {
                return false;
            }
        }
        return true;
    }

    protected int choosePrice() {

        System.out.println(Values.ENTER_PRICE);

        try {
            return Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return Values.ZERO;
    }

    protected void removeSession(User user) {

        Session session = chooseSession(sessionService.getAll());

        if (session != null) {
            sessionService.delete(session.getId());
            ticketService.removeTickets(session);
            System.out.println(Values.SESSION_REMOVED);
        } else {
            System.out.println(Values.DELETION_FILED);
        }
        sessionsMenu(user);

    }

    protected void revokeTicket(User user) {

        try {
            User processedUser = chooseUser();
            Ticket ticket = chooseTicket(processedUser);
            ticketService.returnTicket(ticket);
            System.out.println(Values.TICKET_RETURNED);
            log.info(Values.LOG_TICKET_REVOKING, user.getRole(), user.getUsername(), processedUser.getUsername(), ticket.getId());
        } catch (NoTicketsException e) {
            System.out.println(e.getMessage());
        }
        mainMenu(user);
    }

    protected void addTicket(User user) {

        try {
            User processedUser = chooseUser();
            Session session = chooseSession(sessionService.getAll());
            printSeats(ticketService.get(session));
            Ticket ticket = chooseTicket(ticketService.get(session));
            ticket.setBuyer(processedUser);
            ticketService.update(ticket, ticket.getId());
            System.out.printf(Values.TICKET_ADDED + System.lineSeparator(), processedUser.getUsername());
            log.info(Values.LOG_TICKET_ADDING, user.getRole(), user.getUsername(), ticket.getId(), processedUser.getUsername());
        } catch (NoTicketsException e) {
            System.out.println(e.getMessage());
        }

        mainMenu(user);
    }

    protected User chooseUser() {

        System.out.println(Values.CHOOSE_USER_HEAD);
        String act = scan.nextLine();

        try {
            return userService.get(Long.parseLong(act));
        } catch (NumberFormatException e) {
            return userService.get(act);
        }

    }

    public ManagerController(UserController userController, SessionService sessionService, TicketService ticketService, UserService userService, MovieService movieService) {
        super(userController, sessionService, ticketService, userService);
        this.movieService = movieService;
    }

}
