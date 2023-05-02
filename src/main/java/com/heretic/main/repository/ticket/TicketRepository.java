package com.heretic.main.repository.ticket;

import com.heretic.main.model.session.Session;
import com.heretic.main.model.ticket.Ticket;
import com.heretic.main.model.user.User;

import java.util.List;

/**
 * Ticket database interaction
 */
public interface TicketRepository {

    /**
     * Create new ticket in database
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
     * Remove ticket from database by ID
     */
    Ticket delete(Long id);

}
