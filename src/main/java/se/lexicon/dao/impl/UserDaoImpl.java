package se.lexicon.dao.impl;

import se.lexicon.dao.UserDao;
import se.lexicon.dao.impl.db.MeetingCalendarDBConnection;
import se.lexicon.exception.AuthenticationFailedException;
import se.lexicon.exception.UserExpiredException;
import se.lexicon.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public User createUser(String username) {
        String sql = "INSERT INTO ";
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // TODO: 02/10/2023 implement it in continue... 
        }
        return null;
    }

    @Override
    public Optional<User> findByUserName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException {
        return false;
    }
}
