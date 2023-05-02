package com.heretic.main.repository.ticket;

import com.heretic.main.model.session.Session;
import com.heretic.main.model.ticket.Ticket;
import com.heretic.main.model.user.User;
import com.heretic.main.repository.session.SessionRepository;
import com.heretic.main.repository.user.UserRepository;
import com.heretic.main.service.user.UserService;
import com.heretic.main.util.ConnectionManager;
import com.heretic.main.util.Values;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
public class TicketRepositoryImpl implements TicketRepository {

    private final UserService userService;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Override
    public boolean create(Ticket ticket) {

        String insert = "INSERT INTO ticket (SessionID, Position) VALUES (?, ?)";

        try {
            Class.forName(Values.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement(insert);

            statement.setLong(1, ticket.getSession().getId());
            statement.setInt(2, ticket.getSeat());
            statement.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Ticket> getAll() {

        String request = "SELECT * FROM ticket";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Long id = resultSet.getLong("ID");
                Long sessionID = resultSet.getLong("SessionID");
                Session session = sessionRepository.get(sessionID);
                Long userID = resultSet.getLong("UserID");
                User user = userRepository.get(userID, userService);
                int position = resultSet.getInt("Position");

                Ticket ticket = new Ticket(id, session, user, position);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
            System.out.println(e.getMessage());
        }
        return tickets;
    }

    @Override
    public List<Ticket> get(Session session) {

        String request = "SELECT * FROM ticket WHERE SessionID = ?";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(1, session.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Long id = resultSet.getLong("ID");
                Long userID = resultSet.getLong("UserID");
                User user = userRepository.get(userID, userService);
                int position = resultSet.getInt("Position");

                Ticket ticket = new Ticket(id, session, user, position);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
            System.out.println(e.getMessage());
        }
        return tickets;
    }

    @Override
    public List<Ticket> get(User user) {

        String request = "SELECT * FROM ticket WHERE UserID = ?";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Long id = resultSet.getLong("ID");
                Long sessionID = resultSet.getLong("SessionID");
                Session session = sessionRepository.get(sessionID);
                int position = resultSet.getInt("Position");

                Ticket ticket = new Ticket(id, session, user, position);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
            System.out.println(e.getMessage());
        }
        return tickets;
    }

    @Override
    public Ticket get(Long id) {

        String request = "SELECT * FROM ticket WHERE ID = ?";

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long sessionID = resultSet.getLong("SessionID");
                Session session = sessionRepository.get(sessionID);
                Long userID = resultSet.getLong("UserID");
                User user = userRepository.get(userID, userService);
                int position = resultSet.getInt("Position");
                return new Ticket(id, session, user, position);
            }

        } catch (SQLException e) {
            log.warn(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Ticket update(Ticket ticket, Long id) {

        String update = "UPDATE ticket SET SessionID = ?, UserID = ?, Position = ? WHERE ID =?";

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setLong(1, ticket.getSession().getId());
            statement.setLong(2, ticket.getBuyer().getId());
            statement.setInt(3, ticket.getSeat());
            statement.setLong(4, id);

            statement.execute();
            return ticket;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public Ticket delete(Long id) {

        String request = "DELETE FROM ticket WHERE ID=?";
        Ticket ticket = get(id);

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(1, id);
            statement.execute();
            return ticket;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
            return null;
        }

    }

    public TicketRepositoryImpl(UserService userService, SessionRepository sessionRepository, UserRepository userRepository) {
        this.userService = userService;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

}
