package se.lexicon.view;

import se.lexicon.dao.MeetingCalendarDao;
import se.lexicon.model.Meeting;
import se.lexicon.model.MeetingCalendar;
import se.lexicon.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class CalendarConsoleUI implements CalendarView {
    private MeetingCalendarDao meetingCalendarDao;

    public CalendarConsoleUI(MeetingCalendarDao meetingCalendarDao) {
        this.meetingCalendarDao = meetingCalendarDao;
    }


    @Override
    public void displayUser(User user) {
        System.out.println(user.userInfo());
    }

    @Override
    public void displayCalendar(MeetingCalendar meetingCalendar) {
        System.out.println(meetingCalendar.calendarInfo());
        System.out.println("---------------------------------");
    }

    @Override
    public void displayMeetings(List<Meeting> meetings) {
        if (meetings.isEmpty()) {
            System.out.println("No meetings in this calendar.");
        } else {
            meetings.forEach(meeting -> {
                System.out.println(meeting.meetingInfo());
                System.out.println();
            });
        }
    }

    @Override
    public String promoteString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public Meeting promoteMeetingForm(String username) {
        Scanner scanner = new Scanner(System.in);

        MeetingCalendar meetingCalendar;
        if (meetingCalendarDao.findByUserName(username).isEmpty()) {
            System.out.println("You have no calendars. Please create one!");
            meetingCalendar = new MeetingCalendar(promoteCalendarForm(), username);
        } else if (meetingCalendarDao.findByUserName(username).size() == 1) {
            meetingCalendar = new ArrayList<>(meetingCalendarDao.findByUserName(username)).get(0);
            System.out.println("Create a meeting for: " + meetingCalendar.getTitle());
        } else {

            System.out.println("Chose calendar: \n");
            ArrayList<MeetingCalendar> foundCalendars = new ArrayList<>(meetingCalendarDao.findByUserName(username));
            for (int i = 0; i < foundCalendars.size(); i++) {
                System.out.print((i) + ": ");
                System.out.println(foundCalendars.get(i).getTitle());
            }

            int choice;
            // limiting the set of valid symbols using pattern
            while (!scanner.hasNext("^[0-9]+$") || (choice = scanner.nextInt()) < 0 || choice > foundCalendars.size() - 1) {
                System.out.println("Enter a number between 0 and " + (foundCalendars.size() - 1));
                scanner.nextLine(); // Consume and discard the invalid input
            }
            meetingCalendar = foundCalendars.get(choice);

            // consuming leftover input
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }

//        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a Meeting title: ");
        String title = scanner.nextLine();

        System.out.println("Start Date & Time (yyyy-MM-dd HH:mm)");
        String start = scanner.nextLine();
        System.out.println("End Date & Time (yyyy-MM-dd HH:mm)");
        String end = scanner.nextLine();

        System.out.println("Description: ");
        String description = scanner.nextLine();

        LocalDateTime startDateTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endDateTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return new Meeting(title, startDateTime, endDateTime, description, meetingCalendar);
    }

    @Override
    public String promoteCalendarForm() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a Calendar title: ");

        return scanner.nextLine();
    }

    @Override
    public User promoteUserForm() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a username: ");
        String username = scanner.nextLine();

        System.out.println("Enter a password: ");
        String password = scanner.nextLine();

        return new User(username, password);
    }
}
