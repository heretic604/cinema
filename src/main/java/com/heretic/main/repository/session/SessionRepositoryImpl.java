package com.heretic.main.repository.session;

import com.heretic.main.model.movie.Movie;
import com.heretic.main.model.session.Session;
import com.heretic.main.repository.movie.MovieRepository;
import com.heretic.main.util.ConnectionManager;
import com.heretic.main.util.Values;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
public class SessionRepositoryImpl implements SessionRepository {

    private final MovieRepository movieRepository;

    @Override
    public boolean create(Session session) {

        String insert = "INSERT INTO session (Time, MovieName, Price) VALUES (?, ?, ?)";

        try {
            Class.forName(Values.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement(insert);

            statement.setTimestamp(1, Timestamp.valueOf(session.getTime()));
            statement.setString(2, session.getMovie().getName());
            statement.setInt(3, session.getPrice());
            statement.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Session> getAll() {

        String request = "SELECT * FROM session";
        List<Session> sessions = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Long id = resultSet.getLong("ID");
                LocalDateTime time = resultSet.getTimestamp("Time").toLocalDateTime();
                String movieName = resultSet.getString("MovieName");
                Movie movie = movieRepository.get(movieName);
                int price = resultSet.getInt("Price");

                Session session = new Session(id, time, movie, price);
                sessions.add(session);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
        }
        return sessions;
    }

    @Override
    public Session get(LocalDateTime dateTime) {

        String request = "SELECT * FROM session WHERE Time = ?";

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setTimestamp(1, Timestamp.valueOf(dateTime));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String movieName = resultSet.getString("MovieName");
                Movie movie = movieRepository.get(movieName);
                int price = resultSet.getInt("Price");
                return new Session(id, dateTime, movie, price);
            }

        } catch (SQLException e) {
            log.warn(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Session get(Long id) {

        String request = "SELECT * FROM session WHERE ID = ?";

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LocalDateTime time = resultSet.getTimestamp("Time").toLocalDateTime();
                String movieName = resultSet.getString("MovieName");
                Movie movie = movieRepository.get(movieName);
                int price = resultSet.getInt("Price");
                return new Session(id, time, movie, price);
            }

        } catch (SQLException e) {
            log.warn(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Session update(Session session, Long id) {

        String update = "UPDATE session SET Time = ?, MovieName = ?, Price = ? WHERE session.ID = ?;";

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setTimestamp(1, Timestamp.valueOf(session.getTime()));
            statement.setString(2, session.getMovie().getName());
            statement.setInt(3, session.getPrice());
            statement.setString(4, id.toString());

            statement.execute();
            return session;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public Session delete(Long id) {

        String request = "DELETE FROM session WHERE ID = ?";
        Session session = get(id);

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(1, id);
            statement.execute();
            return session;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
            return null;
        }
    }

    public SessionRepositoryImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

}
