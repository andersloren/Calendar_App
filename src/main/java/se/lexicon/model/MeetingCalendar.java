package se.lexicon.model;

import java.util.ArrayList;
import java.util.List;

public class MeetingCalendar {

    private int id;
    private String title;
    private List<Meeting> meetings;
    private String username;

    public MeetingCalendar(String title) {
        this.title = title;
    }

    public MeetingCalendar(String title, String username) {
        this(title);
        this.username = username;
    }

    public MeetingCalendar(int id, String title, String username) {
        this(title, username);
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Meeting> getMeetings() {
        if (meetings == null) meetings = new ArrayList<>();
        return meetings;
    }

    public String getUsername() {
        return username;
    }

    public void addMeeting(Meeting meeting) {
        if (meetings == null) meetings = new ArrayList<>();
        meetings.add(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        if (meetings == null) throw new IllegalArgumentException("Meetings list is null");
        if (meeting == null) throw new IllegalArgumentException("Meeting is null");
        meetings.remove(meeting);
    }

    public String calendarInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Calendar Info:").append("\n");
        stringBuilder.append("Id: " + getId()).append("\n");
        stringBuilder.append("Title: " + getTitle()).append("\n");
        stringBuilder.append("Username: " + getUsername()).append("\n");
        return stringBuilder.toString();
    }

}
