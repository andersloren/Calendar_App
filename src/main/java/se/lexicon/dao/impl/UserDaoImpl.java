package se.lexicon.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.lexicon.dao.UserDao;
import se.lexicon.dao.impl.db.MeetingCalendarDBConnection;
import se.lexicon.exception.AuthenticationFailedException;
import se.lexicon.exception.DuplicateEntryException;
import se.lexicon.exception.MySQLException;
import se.lexicon.exception.UserExpiredException;
import se.lexicon.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger log = LogManager.getLogger(UserDaoImpl.class);

/*    private Connection connection;

    public MeetingDaoImpl(Connection connection) {
        this.connection = connection;
    }*/ // TODO: 03/10/2023 Re-introduce this?

    @Override
    public User createUser(String username) throws DuplicateEntryException {

        Optional<User> potentialDuplicate = findByUserName(username);
        if (potentialDuplicate.isPresent()) {
            throw new DuplicateEntryException("User already exist.");
        }

        String query = "INSERT INTO users(username, _password) VALUES(?, ?)";
        log.info("Creating user: {}", username);


        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            User user = new User(username);
            user.newPassword();
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getHashedPassword());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                String errorMessage = "Creating user failed, no rows affected.";
                log.error(errorMessage + username);
                throw new MySQLException(errorMessage);
            }

            return user;

        } catch (SQLException e) {
            String errorMessage = "Error occurred while creating user: ";
            log.error(errorMessage + username, e);
            throw new MySQLException(errorMessage + username, e);
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
        String query = "SELECT * FROM users WHERE username = ?";
        log.info("Authenticate user: {}", user.getUsername());
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setString(1, user.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                boolean isExpired = resultSet.getBoolean("expired");
                if (isExpired) {
                    log.warn("User is expired. Username: {}", user.getUsername());
                    throw new UserExpiredException("User is Expired. username: " + user.getUsername());
                }

                String hashedPassword = resultSet.getString("_password");
                user.checkHash(hashedPassword);

            } else {
                String errorMessage = "Authentication failed. Invalid credentials.";
                log.warn(errorMessage);
                throw new AuthenticationFailedException(errorMessage);
            }

            return true;

        } catch (
                SQLException e) {
            log.error("Error occurred while authenticating user by username: {}", user.getUsername());
            throw new MySQLException("Error occurred while authenticating user by username: " + user.getUsername(), e);
        }
    }
}