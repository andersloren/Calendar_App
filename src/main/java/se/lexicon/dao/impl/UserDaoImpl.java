package se.lexicon.dao.impl;

import se.lexicon.dao.UserDao;
import se.lexicon.exception.AuthenticationFailedException;
import se.lexicon.exception.MySQLException;
import se.lexicon.exception.UserExpiredException;
import se.lexicon.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User createUser(String username) {
        String query = "INSERT INTO users(username, _password) VALUES(?, ?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
//            System.out.println("So far so good");
            User user = new User(username);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            System.out.println(user.getPassword());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows);
            if (affectedRows == 0) {
                throw new MySQLException("Creating user failed, no rows affected");
            }

            return user;

        } catch (SQLException e) {
            throw new MySQLException("Error occured while creating user: " + username, e);
        }
    }

    @Override
    public Optional<User> findByUserName(String username) {
        String query = "SELECT * FROM meeting_calendar_db WHERE name = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String foundUsername = resultSet.getString("username");
                String foundPassword = resultSet.getString("_password");
                boolean foundExpired = resultSet.getBoolean("expired");
                User user = new User(foundUsername, foundPassword, foundExpired);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new MySQLException("Error occured while finding user by username: " + username, e);
        }
    }

    @Override
    public boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException {
        String query = "SELECT * FROM users WHERE username = ? AND _password = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                boolean isExpired = resultSet.getBoolean("expired");
                if (isExpired) {
                    throw new UserExpiredException("User is Expired. username: " + user);
                }
            } else {
                throw new AuthenticationFailedException("Authentication failed. Invalid credentials.");
            }

            return true;

        } catch (
                SQLException e) {
            throw new MySQLException("Error occured while authenticating user by username: " + user.getUsername(), e);
        }
    }
}