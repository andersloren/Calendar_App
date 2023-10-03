package se.lexicon.dao.impl;

import se.lexicon.dao.UserDao;
import se.lexicon.dao.impl.db.MeetingCalendarDBConnection;
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

/*    private Connection connection;

    public MeetingDaoImpl(Connection connection) {
        this.connection = connection;
    }*/ // TODO: 03/10/2023 Re-introduce this?

    @Override
    public User createUser(String username) {
        String query = "INSERT INTO users(username, _password) VALUES(?, ?)";
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            User user = new User(username);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new MySQLException("Creating user failed, no rows affected");
            }

            return user;

        } catch (SQLException e) { // TODO: 03/10/2023 Change this exception?
            throw new MySQLException("Error occurred while creating user: " + username, e);
        }
    }

    @Override
    public Optional<User> findByUserName(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
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
            e.printStackTrace();
            throw new MySQLException("Error occurred while finding user by username: " + username, e);
        }
    }

    @Override
    public boolean removeUser(String username) {
        String query = "DELETE FROM users WHERE username = ?";
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, username);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new MySQLException("Deleting user failed, no rows affected");
            }

            System.out.println(username + " was deleted from the TABLE users");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Error occurred while deleting user: " + username, e);
        }
    }


    @Override
    public boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException {
        String query = "SELECT * FROM users WHERE username = ? AND _password = ?";
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
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