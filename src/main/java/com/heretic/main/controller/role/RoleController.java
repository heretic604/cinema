package com.heretic.main.controller.role;

import com.heretic.main.exception.NoTicketsException;
import com.heretic.main.model.session.Session;
import com.heretic.main.model.ticket.Ticket;
import com.heretic.main.model.user.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides an interface to user, according to his role
 */
public interface RoleController {

    /**
     * Main user's menu
     */
    void mainMenu(User user);

    /**
     * Prints user's main menu
     */
    void printMainMenu(User user);

    /**
     * Functionality of the main user menu
     */
    void switchMainMenu(User user);

    /**
     * Sessions menu
     */
    void sessionsMenu(User user);

    /**
     * Prints sessions menu
     */
    void printSessionMenu();

    /**
     * Functionality of the sessions showing menu
     */
    void switchSessionMenu(User user);

    /**
     * Sessions showing menu
     */
    void sessionsShowMenu(User user);

    /**
     * Prints sessions showing menu
     */
    void printSessionShowMenu();

    /**
     * Functionality of the sessions showing menu
     */
    void switchSessionShowMenu(User user);

    /**
     * Shows all sessions
     */
    void showSessions(User user);

    /**
     * Shows sessions by date
     */
    void showSessions(User user, LocalDate date);

    /**
     * Select a date for something
     */
    LocalDate selectDate();

    /**
     * Choose one session from the list
     */
    Session chooseSession(List<Session> sessions);

    /**
     * Executes choosing session
     */
    Session executeChoosingSession(List<Session> sessions);

    /**
     * Print lost of tickets
     */
    void printListTickets(List<Ticket> tickets);

    /**
     * User's sessions menu
     */
    void ticketMenu(User user);

    /**
     * Prints user's sessions menu
     */
    void printTicketMenu(User user);

    /**
     * Functionality of the sessions user menu
     */
    void switchTicketMenu(User user);

    /**
     * Gets info from user about ticket, that he wants to return
     */
    void returnTicket(User user);

    /**
     * Show session's tickets
     */
    void showTickets(Session session, User user);

    /**
     * Show ticket's place
     */
    String showPlace(Ticket ticket);

    /**
     * Ticket's buy menu
     */
    void internalSessionMenu(Session session, User user) throws NoTicketsException;

    /**
     * Print internal session menu
     */
    void printInternalSessionMenu();

    /**
     * Functionality of internal session menu
     */
    void switchInternalSessionMenu(Session session, User user);

    /**
     * Starts buying of ticket
     */
    void tryToBuy(Session session, User user);

    /**
     * View movie's information
     */
    void readAboutMovie(Session session, User user);

    /**
     * Prints description about movie
     */
    void printDescription(String description);

    /**
     * Choose ticket for buying
     */
    Ticket chooseTicket(List<Ticket> tickets) throws NoTicketsException;

    /**
     * Select another user's ticket
     */
    Ticket chooseTicket(User user) throws NoTicketsException;

    /**
     * Adds user to ticket
     */
    boolean buyTicket(Ticket ticket, User user);

    /**
     * Prints list of sessions
     */
    void printSessions(List<Session> sessions);

    /**
     * Prints schema of session
     */
    void printSeats(List<Ticket> tickets);

    /**
     * Menu of your account
     */
    void accountMenu(User user);

    /**
     * Prints account menu
     */
    void printAccountMenu(User user);

    /**
     * Prints account menu
     */
    void switchAccountMenu(User user);

    /**
     * Change password
     */
    void changePassword(User user);

    /**
     * Change email
     */
    void changeEmail(User user);

}
