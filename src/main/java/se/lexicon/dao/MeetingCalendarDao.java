package se.lexicon.dao;

import se.lexicon.model.MeetingCalendar;

import java.util.Collection;
import java.util.Optional;

public interface MeetingCalendarDao {

    MeetingCalendar createMeetingCalendar(String title, String username);

    Optional<MeetingCalendar> findById(int id);

    Collection<MeetingCalendar> findByUserName(String username);

    Optional<MeetingCalendar> findByTitle(String title);

    boolean deleteCalendar(int id);

}
