package se.lexicon.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.lexicon.dao.MeetingCalendarDao;
import se.lexicon.dao.impl.MeetingCalendarDaoImpl;
import se.lexicon.dao.impl.MeetingDaoImpl;
import se.lexicon.dao.impl.db.MeetingCalendarDBConnection;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MeetingTest {

    private MeetingDaoImpl testObject;


    @BeforeEach
    public void setup() {
        testObject = new MeetingDaoImpl();
    }

    @Test
    public void testCreateMeeting() {
        MeetingCalendarDao meetingCalendarDao = new MeetingCalendarDaoImpl();
        Optional<MeetingCalendar> foundCalendar = meetingCalendarDao.findById(8);

        Meeting expectedMeeting = new Meeting(
                "title2",
                LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 1, 1, 1, 0),
                "1 hour meeting",
                foundCalendar.orElse(null));

        Meeting actualMeeting = testObject.createMeeting(expectedMeeting);
        Assertions.assertEquals(expectedMeeting, actualMeeting);
        System.out.println(expectedMeeting);
        System.out.println(actualMeeting);
    }

    @Test
    @DisplayName("Should return meeting found by id")
    void testFindById() {
        int id = 3;
        Optional<Meeting> foundMeeting = testObject.findById(id);
        if (foundMeeting.isPresent()) {
            Assertions.assertEquals(id, foundMeeting.get().getId());
            System.out.println(id + "\n" + foundMeeting.get().getId());

        }
    }

    @Test
    @DisplayName("Should return list of meetings by calendar Id")
    void testFindAllMeetingsByCalendarId() {
        int id = 8;
        Collection<Meeting> foundMeetingsById = new ArrayList<>();

        foundMeetingsById = testObject.findAllMeetingsByCalendarId(8);
        foundMeetingsById.forEach(System.out::println);
    }

    @Test
    @DisplayName("Should delete meeting by id")
    void testDeleteMeeting() {
        int id = 4;
        Assertions.assertTrue(testObject.deleteMeeting(id));
    }
}