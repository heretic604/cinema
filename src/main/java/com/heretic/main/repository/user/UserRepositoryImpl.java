package com.heretic.main.repository.user;

import com.heretic.main.model.user.Role;
import com.heretic.main.model.user.Status;
import com.heretic.main.model.user.User;
import com.heretic.main.service.user.UserService;
import com.heretic.main.util.ConnectionManager;
import com.heretic.main.util.Values;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
public class UserRepositoryImpl implements UserRepository {

    @Override
    public boolean create(User user) {

        String insert = "INSERT INTO person (Username, Password, Email, Role, Status, Birthday) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Class.forName(Values.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement(insert);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole().getTitle());
            statement.setString(5, user.getStatus().getTitle());
            statement.setDate(6, Date.valueOf(user.getBirthday()));
            statement.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
            return false;
        }
    }

    @Override
    public List<User> getAll(UserService userService) {

        String request = "SELECT * FROM person";
        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String email = resultSet.getString("Email");
                Role role = userService.checkRole(resultSet.getString("Role"));
                Status status = userService.checkStatus(resultSet.getString("Status"));
                LocalDate birthday = resultSet.getDate("Birthday").toLocalDate();

                User user = new User(id, username, password, email, status, role, birthday);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
        }
        return users;
    }

    @Override
    public User get(Long id, UserService userService) {

        String request = "SELECT * FROM person WHERE ID = ?";

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String email = resultSet.getString("Email");
                Role role = userService.checkRole(resultSet.getString("Role"));
                Status status = userService.checkStatus(resultSet.getString("Status"));
                LocalDate birthday = resultSet.getDate("Birthday").toLocalDate();
                return new User(id, username, password, email, status, role, birthday);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public User get(String username, UserService userService) {

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE Username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String password = resultSet.getString("Password");
                String email = resultSet.getString("Email");
                Role role = userService.checkRole(resultSet.getString("Role"));
                Status status = userService.checkStatus(resultSet.getString("Status"));
                LocalDate birthday = resultSet.getDate("Birthday").toLocalDate();
                return new User(id, username, password, email, status, role, birthday);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public User update(User user, Long id) {

        String update = "UPDATE person SET Password = ?, Email = ?, Role = ?, Status = ?  WHERE person.ID =?;";

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getRole().getTitle());
            statement.setString(4, user.getStatus().getTitle());
            statement.setString(5, id.toString());

            statement.execute();
            return user;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public User delete(Long id, UserService userService) {

        String request = "DELETE FROM person WHERE ID=?";
        User user = get(id, userService);

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(1, id);
            statement.execute();
            return user;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
            return null;
        }

    }

    public UserRepositoryImpl() {
    }

}
