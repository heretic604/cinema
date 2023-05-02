package com.heretic.main.service.ticket;

import com.heretic.main.model.session.Session;
import com.heretic.main.model.ticket.Ticket;
import com.heretic.main.model.user.User;

import java.util.List;

/**
 * Ticket interaction
 */
public interface TicketService {

    /**
     * Create new ticket
     */
    boolean create(Ticket ticket);

    /**
     * View all tickets
     */
    List<Ticket> getAll();

    /**
     * View tickets for a session
     */
    List<Ticket> get(Session session);

    /**
     * Get user's tickets
     */
    List<Ticket> get(User user);

    /**
     * Get ticket by ID
     */
    Ticket get(Long id);

    /**
     * Change ticket
     */
    Ticket update(Ticket ticket, Long id);

    /**
     * Remove ticket by ID
     */
    Ticket delete(Long id);

    /**
     * Return ticket to pool
     */
    boolean returnTicket(Ticket ticket);

    /**
     * Create tickets for new session
     */
    void createTickets (Session session);

    /**
     * Removes tickets on session deletion
     */
    void removeTickets (Session session);

}
