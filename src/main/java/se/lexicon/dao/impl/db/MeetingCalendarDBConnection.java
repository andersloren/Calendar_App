package se.lexicon.dao.impl.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.lexicon.dao.MeetingDao;
import se.lexicon.exception.DBConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MeetingCalendarDBConnection {

    private static final Logger log = LogManager.getLogger(MeetingCalendarDBConnection.class);
    private static final String DB_NAME = "meeting_calendar_db";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "1234";

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DBConnectionException("Failed to connect to DB (" + DB_NAME + ")", e);
        }
    }
}
