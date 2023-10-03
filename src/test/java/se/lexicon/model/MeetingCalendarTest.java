package se.lexicon.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.lexicon.dao.impl.MeetingCalendarDaoImpl;

import java.util.Collection;
import java.util.Optional;

public class MeetingCalendarTest {

    private MeetingCalendarDaoImpl testObject;

    @BeforeEach
    public void setup() {
        testObject = new MeetingCalendarDaoImpl();
    }

    @Test
    void testCreateMeetingCalendar() {
        String meetingCalendarTitle = "title2";
        String meetingCalendarUsername = "anders";
        MeetingCalendar createdMeetingCalendar = testObject.createMeetingCalendar(meetingCalendarTitle, meetingCalendarUsername);

        Assertions.assertEquals(meetingCalendarUsername, createdMeetingCalendar.getUsername());
    }

    @Test
    @DisplayName("Should return MeetingCalendar if found by id")
    void testFindById() {
        int id = 1;
        Optional<MeetingCalendar> meetingCalendar = testObject.findById(id);
        meetingCalendar.ifPresent(value -> System.out.println(value.calendarInfo()));
    }

    @Test
    @DisplayName("Should return list of all calendars belonging to specific username")
    void testFindByUserName() {
        String username = "anders";
        Collection<MeetingCalendar> foundCalendarsByUsername = testObject.findByUserName(username);

        foundCalendarsByUsername.forEach(System.out::println);
    }

    @Test
    @DisplayName("Should return list of all calendars belonging to specific username")
    void testFindByTitle() {
        String title = "Calendar";
        Collection<MeetingCalendar> foundCalendarsByUsername = testObject.findByTitle(title);

        foundCalendarsByUsername.forEach(System.out::println);
    }

    @Test
    @DisplayName("Should return true if calendar of passed id was deleted")
    void testDeleteCalendar() {
        int id = 7;
        Assertions.assertTrue(testObject.deleteCalendar(id));

    }
}
