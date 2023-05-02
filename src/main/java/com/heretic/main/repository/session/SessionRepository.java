package com.heretic.main.repository.session;

import com.heretic.main.model.session.Session;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Session database interaction
 */
public interface SessionRepository {

    /**
     * Create new session in database
     */
    boolean create(Session session);

    /**
     * View all sessions
     */
    List<Session> getAll();

    /**
     * Get session by ID
     */
    Session get(Long id);

    /**
     * Get session by time
     */
    Session get(LocalDateTime dateTime);

    /**
     * Change session
     */
    Session update(Session session, Long id);

    /**
     * Remove session from database by ID
     */
    Session delete(Long id);

}
