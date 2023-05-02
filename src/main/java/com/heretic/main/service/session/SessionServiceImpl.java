package com.heretic.main.service.session;

import com.heretic.main.model.session.Session;
import com.heretic.main.repository.session.SessionRepository;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public boolean create(Session session) {
        return sessionRepository.create(session);
    }

    @Override
    public List<Session> getAll() {
        return sessionRepository.getAll();
    }

    @Override
    public Session get(LocalDateTime dateTime) {
        return sessionRepository.get(dateTime);
    }

    @Override
    public List<Session> get(LocalDate date) {

        List<Session> sessions = sessionRepository.getAll();

        return sessions.stream()
                .filter(s -> s.getTime().toLocalDate().equals(date))
                .toList();
    }

    @Override
    public Session get(Long id) {
        return sessionRepository.get(id);
    }

    @Override
    public Session update(Session session, Long id) {
        return sessionRepository.update(session, id);
    }

    @Override
    public Session delete(Long id) {
        return sessionRepository.delete(id);
    }

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

}
