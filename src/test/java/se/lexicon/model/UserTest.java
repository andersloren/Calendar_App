package se.lexicon.model;

import org.junit.jupiter.api.*;
import se.lexicon.dao.impl.UserDaoImpl;
import se.lexicon.exception.AuthenticationFailedException;
import se.lexicon.exception.DuplicateEntryException;
import se.lexicon.exception.UserExpiredException;

import java.util.Optional;

public class UserTest {

    private UserDaoImpl testObject;

    @BeforeEach
    public void setup() {
        testObject = new UserDaoImpl();
    }

    @Test
    @DisplayName("Create user, should equal returned user")
    void testCreateUser() throws DuplicateEntryException {
        String username = "mikael";
        User createdUser1 = testObject.createUser(username);

        Assertions.assertEquals(username, createdUser1.getUsername());

        System.out.println(createdUser1.userInfo());
    }

    @Test
    @DisplayName("Return Optional<User> by username from TABLE users")
    void testFindByUserName() {
        String username = "Anders";
        Optional<User> user = testObject.findByUserName(username);
        user.ifPresent(value -> System.out.println(value.userInfo()));
    }

    @Test
    @DisplayName("Returns true if user was removed")
    void testRemoveUser() {
        String username = "test";
        Assertions.assertTrue(testObject.removeUser(username));
    }

    @Test
    @DisplayName("Returns true if passed in user exist in TABLE users")
    void testAuthentication() throws AuthenticationFailedException, UserExpiredException, DuplicateEntryException {
        String username = "test";
        User user = testObject.createUser(username);
        boolean b = testObject.authenticate(user);
        Assertions.assertTrue(b);
        testObject.removeUser(username);
    }
}