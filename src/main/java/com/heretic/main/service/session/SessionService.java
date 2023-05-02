package com.heretic.main.service.session;

import com.heretic.main.model.session.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Session interaction
 */
public interface SessionService {

    /**
     * Create new session
     */
    boolean create(Session session);

    /**
     * View all sessions
     */
    List<Session> getAll();

    /**
     * Get sessions by date
     */
    List<Session> get(LocalDate date);

    /**
     * Get session by time
     */
    Session get(LocalDateTime dateTime);

    /**
     * Get session by ID
     */
    Session get(Long id);

    /**
     * Change session
     */
    Session update(Session session, Long id);

    /**
     * Remove session by ID
     */
    Session delete(Long id);

}
