package com.heretic.main.repository.movie;

import com.heretic.main.model.movie.Movie;
import com.heretic.main.util.ConnectionManager;
import com.heretic.main.util.Values;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
public class MovieRepositoryImpl implements MovieRepository {

    @Override
    public boolean create(Movie movie) {

        String insert = "INSERT INTO movie (Name, IMDb, AgeLimit, Description) VALUES (?, ?, ?, ?)";

        try {
            Class.forName(Values.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement(insert);

            statement.setString(1, movie.getName());
            statement.setDouble(2, movie.getIMDb());
            statement.setInt(3, movie.getAgeLimit());
            statement.setString(4, movie.getDescription());
            statement.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Movie> getAll() {

        List<Movie> movies = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Long id = resultSet.getLong("ID");
                String name = resultSet.getString("Name");
                double IMDb = resultSet.getDouble("IMDb");
                int ageLimit = resultSet.getInt("AgeLimit");
                String description = resultSet.getString("Description");

                Movie movie = new Movie(id, name, IMDb, ageLimit, description);
                movies.add(movie);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
        }
        return movies;
    }

    @Override
    public Movie get(Long id) {

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie WHERE ID=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("Name");
                double IMDb = resultSet.getDouble("IMDb");
                int ageLimit = resultSet.getInt("AgeLimit");
                String description = resultSet.getString("Description");

                return new Movie(id, name, IMDb, ageLimit, description);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public Movie get(String name) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie WHERE Name=?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                double IMDb = resultSet.getDouble("IMDb");
                int ageLimit = resultSet.getInt("AgeLimit");
                String description = resultSet.getString("Description");

                return new Movie(id, name, IMDb, ageLimit, description);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public Movie delete(Long id) {

        Movie movie = get(id);

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM movie WHERE ID=?");
            statement.setLong(1, id);
            statement.execute();
            return movie;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
        }
        return null;
    }

    public MovieRepositoryImpl() {
    }

}
