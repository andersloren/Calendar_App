package se.lexicon.dao.impl;

import se.lexicon.dao.MeetingCalendarDao;
import se.lexicon.dao.impl.db.MeetingCalendarDBConnection;
import se.lexicon.exception.MySQLException;
import se.lexicon.model.MeetingCalendar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MeetingCalendarDaoImpl implements MeetingCalendarDao {

/*    private Connection connection;

    public MeetingCalendarDaoImpl(Connection connection) {
        this.connection = connection;
    }*/ // TODO: 03/10/2023 Re-introduce this?

    @Override
    public MeetingCalendar createMeetingCalendar(String title, String username) {
        String query = "INSERT INTO meeting_calendars(title, username) VALUES(?, ?)";
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            MeetingCalendar meetingCalendar = new MeetingCalendar(title, username);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, username);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new MySQLException("Creating MeetingCalendar failed, no rows affected");
            }

            return meetingCalendar;

        } catch (SQLException e) { // TODO: 03/10/2023 Change this exception?
            throw new MySQLException("Error occurred while creating meeting calendar: " + title);
        }
    }

    @Override
    public Optional<MeetingCalendar> findById(int id) {
        String query = "SELECT * FROM meeting_calendars WHERE id = ?";
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int foundId = resultSet.getInt("id");
                String foundTitle = resultSet.getString("title");
                String foundUsername = resultSet.getString("username");


                MeetingCalendar meetingCalendar = new MeetingCalendar(foundId, foundTitle, foundUsername);
                return Optional.of(meetingCalendar);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Error occurred while finding meeting_calendar by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<MeetingCalendar> findByUserName(String username) {
        String query = "SELECT * FROM meeting_calendars WHERE username = ?";
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<MeetingCalendar> calendarsFoundByUserName = new ArrayList<>();
            while (resultSet.next()) {
                int foundId = resultSet.getInt("id");
                String foundTitle = resultSet.getString("title");
                String foundUsername = resultSet.getString("username");

                MeetingCalendar meetingCalendar = new MeetingCalendar(foundId, foundTitle, foundUsername);
                calendarsFoundByUserName.add(meetingCalendar);
            }

            return calendarsFoundByUserName;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Error occurred while finding meeting_calendar by username: " + username, e);
        }
    }

    @Override
    public Collection<MeetingCalendar> findByTitle(String title) {
        String query = "SELECT * FROM meeting_calendars WHERE title = ?";
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setString(1, title);

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<MeetingCalendar> calendarsFoundByTitle = new ArrayList<>();
            while (resultSet.next()) {
                int foundId = resultSet.getInt("id");
                String foundTitle = resultSet.getString("title");
                String foundUsername = resultSet.getString("username");

                MeetingCalendar meetingCalendar = new MeetingCalendar(foundId, foundTitle, foundUsername);
                calendarsFoundByTitle.add(meetingCalendar);
            }

            return calendarsFoundByTitle;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Error occurred while finding meeting_calendar by title: " + title, e);
        }
    }

    @Override
    public boolean deleteCalendar(int id) {
        String query = "DELETE FROM meeting_calendars WHERE id = ?";
        try (
                Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new MySQLException("Deleting MeetingCalendar failed, no rows affected");
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Error occurred while deleting meeting_calendar by id: " + id, e);
        }
    }
}
