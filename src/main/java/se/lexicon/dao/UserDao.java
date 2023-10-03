package se.lexicon.dao;

import se.lexicon.exception.AuthenticationFailedException;
import se.lexicon.exception.MySQLException;
import se.lexicon.exception.UserExpiredException;
import se.lexicon.model.User;

import java.util.Optional;

public interface UserDao {
    User createUser(String username);

    Optional<User> findByUserName(String username);

    boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException;
}

