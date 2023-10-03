package se.lexicon.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingCalendarTest  {

    @Test
    void testAdd() {
        MeetingCalendar meetingCalendar = new MeetingCalendar(
                "calendar title",
                "anders");
        Meeting meeting = new Meeting(
                "title",
                LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 1, 1, 0, 1),
                "description");
        meetingCalendar.addMeeting(meeting);
        List<Meeting> meetings = meetingCalendar.getMeetings();
        meetings.forEach(System.out::println);
    }
    }
