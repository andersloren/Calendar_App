package se.lexicon;

import se.lexicon.dao.UserDao;
import se.lexicon.dao.impl.UserDaoImpl;
import se.lexicon.dao.impl.db.MeetingCalendarDBConnection;
import se.lexicon.exception.AuthenticationFailedException;
import se.lexicon.exception.CalendarExceptionHandler;
import se.lexicon.exception.UserExpiredException;
import se.lexicon.model.User;

import java.sql.SQLException;
import java.util.Optional;

public class App {
    public static void main(String[] args) {

        try {
            UserDao userDao = new UserDaoImpl(MeetingCalendarDBConnection.getConnection());
            User user = new User("admin");
            User createdUser = userDao.createUser(user.getUsername());
            System.out.println("CratedUser.userInfo() + " + createdUser.userInfo());
            Optional<User> userOptional = userDao.findByUserName("admin");
            if (userOptional.isPresent()) {
                System.out.println(userOptional.get().userInfo());
            }

            try {
                userDao.authenticate(new User("admin", "123456"));
            } catch (AuthenticationFailedException e) {
                System.out.println(e.getMessage());
            } catch (UserExpiredException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            CalendarExceptionHandler.handelException(e);
        }
    }
}
