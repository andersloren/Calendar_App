package se.lexicon;

import se.lexicon.dao.MeetingCalendarDao;
import se.lexicon.dao.impl.MeetingCalendarDaoImpl;
import se.lexicon.dao.impl.db.MeetingCalendarDBConnection;
import se.lexicon.exception.CalendarExceptionHandler;
import se.lexicon.model.MeetingCalendar;

public class App {
    public static void main(String[] args) {

        //test UserDaoImpl
//        try {
//            UserDao userDao = new UserDaoImpl(MeetingCalendarDBConnection.getConnection());
//            User user = new User("admin2");
//            User createdUser = userDao.createUser(user.getUsername());
//            System.out.println("CreatedUser.userInfo() + " + createdUser.userInfo());
//            Optional<User> userOptional = userDao.findByUserName("admin");
//            if (userOptional.isPresent()) {
//                System.out.println(userOptional.get().userInfo());
//            }
//
//            try {
//                userDao.authenticate(new User("admin", "123456"));
//            } catch (AuthenticationFailedException e) {
//                System.out.println(e.getMessage());
//            } catch (UserExpiredException e) {
//                System.out.println(e.getMessage());
//            }
//        } catch (Exception e) {
//            CalendarExceptionHandler.handleException(e);
//        }

        //test MeetingCalendarDaoImpl
//        try {
//            MeetingCalendarDao meetingCalendarDao = new MeetingCalendarDaoImpl(MeetingCalendarDBConnection.getConnection());
//            MeetingCalendar createdMeetingCalendar = meetingCalendarDao.createMeetingCalendar("New Meeting Calendar", "Anders Loren");
//            System.out.println("CreatedUser.userInfo() + " + createdMeetingCalendar.calendarInfo());
//        } catch (Exception e) {
//            CalendarExceptionHandler.handleException(e);
//            }
//        }

        //test MeetingDaoImpl
//        try {
//            MeetingDao meetingDao = new MeetingDaoImpl(MeetingCalendarDBConnection.getConnection());
//            Meeting meeting = new Meeting(
//                    "title",
//                    LocalDateTime.of(2023, 1, 1, 0, 0),
//                    LocalDateTime.of(2023, 1, 1, 1, 0),
//                    "1 hour meeting");
//            Meeting createdMeeting = meetingDao.createMeeting(meeting);
//            System.out.println("CreatedUser.userInfo() + " + createdMeeting.meetingInfo());
//            //Optional<Meeting> later when I have findById done?
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
    }
}
