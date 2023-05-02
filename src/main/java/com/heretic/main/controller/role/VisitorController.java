package com.heretic.main.controller.role;

import com.heretic.main.controller.UserController;
import com.heretic.main.exception.IncorrectEmailException;
import com.heretic.main.exception.NoTicketsException;
import com.heretic.main.model.session.Session;
import com.heretic.main.model.ticket.Ticket;
import com.heretic.main.model.user.User;
import com.heretic.main.service.session.SessionService;
import com.heretic.main.service.ticket.TicketService;
import com.heretic.main.service.user.UserService;
import com.heretic.main.util.Values;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class VisitorController implements RoleController {

    protected Scanner scan = new Scanner(System.in);

    protected UserController userController;
    protected SessionService sessionService;
    protected TicketService ticketService;
    protected UserService userService;

    @Override
    public void mainMenu(User user) {
        printMainMenu(user);
        switchMainMenu(user);
    }

    @Override
    public void printMainMenu(User user) {
        System.out.printf(Values.MAIN_MENU_HEAD, LocalDate.now(), LocalDate.now().getDayOfWeek(), user.getUsername(), user.getRole().getTitle());
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.LOGOUT);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.CHECK_ACCOUNT);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Values.VIEW_TICKETS);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.THREE_STR, Values.VIEW_SESSIONS);
    }

    @Override
    public void switchMainMenu(User user) {
        switch (scan.nextLine()) {
            case Values.ONE_STR -> accountMenu(user);
            case Values.TWO_STR -> ticketMenu(user);
            case Values.THREE_STR -> sessionsMenu(user);
            default -> {
                log.info(Values.LOG_LOGOUT, user.getRole().getTitle(), user.getUsername());
                userController.startMenu();
            }
        }
    }

    @Override
    public void accountMenu(User user) {
        printAccountMenu(user);
        switchAccountMenu(user);
    }

    @Override
    public void printAccountMenu(User user) {
        System.out.printf(Values.ACC_INFO, user.getId(), user.getUsername(), user.getEmail(), user.getBirthday().toString());
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.CHANGE_PASSWORD);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Values.CHANGE_EMAIL);
    }

    @Override
    public void switchAccountMenu(User user) {
        switch (scan.nextLine()) {
            case Values.ONE_STR -> changePassword(user);
            case Values.TWO_STR -> changeEmail(user);
            default -> mainMenu(user);
        }
    }

    @Override
    public void changePassword(User user) {
        userController.checkPassword(user);
        user.setPassword(userService.encryptPassword(userController.enterPassword(), user.getUsername()));
        userService.update(user, user.getId());
        System.out.println(Values.CHANGES_SAVED);
        log.info(Values.LOG_PASSWORD_CHANGED, user.getRole().getTitle(), user.getUsername());
        accountMenu(user);
    }

    @Override
    public void changeEmail(User user) {

        try {
            user.setEmail(userController.enterEmail());
            userService.update(user, user.getId());
            System.out.println(Values.CHANGES_SAVED);
        } catch (IncorrectEmailException e) {
            System.out.println(e.getMessage());
        }

        log.info(Values.LOG_EMAIL_CHANGED, user.getRole().getTitle(), user.getUsername(), user.getEmail());
        accountMenu(user);
    }

    @Override
    public void ticketMenu(User user) {
        printTicketMenu(user);
        switchTicketMenu(user);
    }

    @Override
    public void printTicketMenu(User user) {
        System.out.println(Values.TICKET_MENU_HEAD);
        printListTickets(ticketService.get(user));
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.RETURN_TICKET);
    }

    @Override
    public void switchTicketMenu(User user) {

        if (scan.nextLine().equals(Values.ONE_STR)) {
            returnTicket(user);
        } else {
            mainMenu(user);
        }
    }

    @Override
    public void sessionsMenu(User user) {
        printSessionMenu();
        switchSessionMenu(user);
    }

    @Override
    public void printSessionMenu() {
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.SHOW_SESSIONS);
    }

    @Override
    public void switchSessionMenu(User user) {
        if (scan.nextLine().equals(Values.ONE_STR)) {
            sessionsShowMenu(user);
        } else mainMenu(user);
    }

    @Override
    public void sessionsShowMenu(User user) {
        printSessionShowMenu();
        switchSessionShowMenu(user);
    }

    @Override
    public void printSessionShowMenu() {
        System.out.println(Values.SESSION_SHOW_MENU_HEAD);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.SHOW_ALL_SESSIONS);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Values.SHOW_TODAY_SESSIONS);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.THREE_STR, Values.SHOW_SESSIONS_DATE);
    }

    @Override
    public void switchSessionShowMenu(User user) {
        switch (scan.nextLine()) {
            case Values.ONE_STR -> showSessions(user);
            case Values.TWO_STR -> showSessions(user, LocalDate.now());
            case Values.THREE_STR -> showSessions(user, selectDate());
            default -> mainMenu(user);
        }
    }

    @Override
    public void showSessions(User user) {

        Session session = chooseSession(sessionService.getAll());

        if (session != null) {
            showTickets(session, user);
        } else {
            sessionsShowMenu(user);
        }
    }

    @Override
    public void showSessions(User user, LocalDate date) {

        Session session = chooseSession(sessionService.get(date));

        if (session != null) {
            showTickets(session, user);
        } else {
            sessionsShowMenu(user);
        }
    }

    @Override
    public LocalDate selectDate() {

        System.out.println(Values.CHOOSE_DATE_SESSION);

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();

        try {
            month = Integer.parseInt(scan.nextLine());
            day = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return LocalDate.of(year, month, day);
    }

    @Override
    public Session chooseSession(List<Session> sessions) {

        System.out.println(Values.CHOOSE_SESSION);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);

        printSessions(sessions);
        return executeChoosingSession(sessions);

    }

    @Override
    public void printSessions(List<Session> sessions) {
        for (int i = Values.ZERO; i < sessions.size(); i++) {
            System.out.printf(Values.SAMPLE + System.lineSeparator(), (i + Values.ONE), sessions.get(i));
        }
    }

    @Override
    public Session executeChoosingSession(List<Session> sessions) {

        int act = Values.ZERO;

        try {
            act = Integer.parseInt(scan.nextLine());
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
        }

        if (act <= Values.ZERO || act > sessions.size()) {
            return null;
        } else {
            return sessions.get(act - Values.ONE);
        }
    }

    @Override
    public void printListTickets(List<Ticket> tickets) {

        if (tickets.size() == Values.ZERO) {
            System.out.println(Values.NO_TICKETS);
        } else {
            for (int i = Values.ZERO; i < tickets.size(); i++) {
                System.out.printf(Values.SAMPLE + System.lineSeparator(), (i + Values.ONE), tickets.get(i));
            }
        }
    }

    @Override
    public void returnTicket(User user) {

        try {
            Ticket ticket = chooseTicket(user);
            if (ticketService.returnTicket(ticket)) {
                System.out.println(Values.TICKET_RETURNED);
                log.info(Values.LOG_TICKET_RETURNED, user.getRole().getTitle(), user.getUsername(), ticket.getId());
            }
        } catch (NoTicketsException e) {
            System.out.println(e.getMessage());
            log.info(e.getMessage());
        }

        mainMenu(user);
    }

    @Override
    public void showTickets(Session session, User user) {

        System.out.println(session + System.lineSeparator());

        printSeats(ticketService.get(session));

        try {
            internalSessionMenu(session, user);
        } catch (NoTicketsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void printSeats(List<Ticket> tickets) {
        for (int i = Values.ZERO; i < tickets.size(); i++) {
            System.out.print(showPlace(tickets.get(i)));
            if ((i + Values.ONE) % Values.TEN == Values.ZERO) {
                System.out.println(System.lineSeparator());
            }
        }
    }

    @Override
    public String showPlace(Ticket ticket) {

        if (ticket.getBuyer() != null) {
            return Values.OCCUPIED_SEAT;
        } else if (ticket.getSeat() < Values.MORNING_HOUR) {
            return String.format(Values.SMALL_SEAT_SAMPLE, ticket.getSeat());
        } else return String.format(Values.BIG_SEAT_SAMPLE, ticket.getSeat());

    }

    @Override
    public void internalSessionMenu(Session session, User user) throws NoTicketsException {
        printInternalSessionMenu();
        switchInternalSessionMenu(session, user);
    }

    @Override
    public void printInternalSessionMenu() {
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ONE_STR, Values.BUY_TICKET);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.TWO_STR, Values.READ_ABOUT_MOVIE);
    }

    @Override
    public void switchInternalSessionMenu(Session session, User user) {
        switch (scan.nextLine()) {
            case Values.ONE_STR -> tryToBuy(session, user);
            case Values.TWO_STR -> readAboutMovie(session, user);
            default -> sessionsShowMenu(user);
        }
    }

    @Override
    public void readAboutMovie(Session session, User user) {

        System.out.println(session.getMovie());

        printDescription(session.getMovie().getDescription());

        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);
        scan.nextLine();

        log.info(Values.LOG_READ_ABOUT, user.getRole().getTitle(), user.getUsername(), session.getMovie().getName());
        showTickets(session, user);
    }

    @Override
    public void printDescription(String description) {

        char[] chars = description.toCharArray();
        int count = Values.ZERO;

        for (char symbol : chars) {
            if ((count >= Values.CHARS_TO_LINE && symbol == Values.SPACE)) {
                System.out.println(symbol);
                count = Values.ZERO;
            } else {
                System.out.print(symbol);
                count++;
            }
        }

        System.out.println();
    }

    @Override
    public void tryToBuy(Session session, User user) {

        List<Ticket> tickets = ticketService.get(session);

        try {
            System.out.println(buyTicket(chooseTicket(tickets), user) ? Values.SUCCESSFUL_PURCHASE : Values.UNSUCCESSFUL_PURCHASE);
        } catch (NoTicketsException e) {
            System.out.println(e.getMessage());
        }

        mainMenu(user);
    }

    @Override
    public Ticket chooseTicket(List<Ticket> tickets) throws NoTicketsException {
        System.out.println(Values.SELECT_TICKET);
        int act = Integer.parseInt(scan.nextLine());
        for (Ticket ticket : tickets) {
            if (act == ticket.getSeat()) {
                return ticket;
            }
        }
        throw new NoTicketsException();
    }

    @Override
    public Ticket chooseTicket(User user) throws NoTicketsException {

        System.out.println(Values.CHOOSE_TICKET);
        System.out.printf(Values.SAMPLE + System.lineSeparator(), Values.ZERO_STR, Values.RETURN);

        int act = Values.ZERO;
        List<Ticket> tickets = ticketService.get(user);

        printListTickets(tickets);

        try {
            act = Integer.parseInt(scan.nextLine());
        } catch (InputMismatchException e) {
            System.out.println(Values.INCORRECT_INPUT);
        }

        if (act <= tickets.size() && act > Values.ZERO) {
            return tickets.get(act - Values.ONE);
        } else throw new NoTicketsException();
    }

    @Override
    public boolean buyTicket(Ticket ticket, User user) {
        if (ticket == null) {
            System.out.println(Values.INCORRECT_INPUT);
        } else if (!userService.checkAge(ticket, user)) {
            System.out.println(Values.AGE_LIMIT_MESSAGE);
        } else if (ticket.getBuyer() != null) {
            System.out.println(Values.TICKED_PURCHASED);
        } else {
            ticket.setBuyer(user);
            ticketService.update(ticket, ticket.getId());
            log.info(Values.LOG_PURCHASE_COMPLETE, user.getRole().getTitle(), user.getUsername(), ticket.getId());
            return true;
        }
        log.info(Values.LOG_PURCHASE_FAIL, user.getRole().getTitle(), user.getUsername());
        return false;
    }

    public VisitorController(UserController userController, SessionService sessionService, TicketService ticketService, UserService userService) {
        this.userController = userController;
        this.sessionService = sessionService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

}
