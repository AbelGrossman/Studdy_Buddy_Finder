package fr.pantheonsorbonne.cri.MeetingFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class MeetingMembersDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static void insertMeetingParticipantIntoDatabase(Meeting meeting, User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO MeetingParticipants (meeting_id, user_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, meeting.getMeetingId());
                preparedStatement.setInt(2, user.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting meeting participant into the database: " + e.getMessage());
        }
    }

    public static void removeMeetingParticipantFromDatabase(Meeting meeting, User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM MeetingParticipants WHERE meeting_id = ? AND user_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, meeting.getMeetingId());
                preparedStatement.setInt(2, user.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting meeting participant from the database: " + e.getMessage());
        }
    }

    public static void removeMeetingParticipants(Meeting meeting) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM MeetingParticipants WHERE meeting_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, meeting.getMeetingId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting meeting participants from the database: " + e.getMessage());
        }
    }

}
