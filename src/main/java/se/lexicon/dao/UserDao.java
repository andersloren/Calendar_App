package se.lexicon.dao;

import se.lexicon.exception.AuthenticationFailedException;
import se.lexicon.exception.DuplicateEntryException;
import se.lexicon.exception.UserExpiredException;
import se.lexicon.model.User;

import java.util.Optional;

public interface UserDao {
    User createUser(String username) throws DuplicateEntryException;

    Optional<User> findByUserName(String username);

    boolean removeUser(String username);

    boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException;
}

