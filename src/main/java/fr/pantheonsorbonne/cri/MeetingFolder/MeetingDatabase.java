package fr.pantheonsorbonne.cri.MeetingFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import fr.pantheonsorbonne.cri.GroupFolder.*;
import fr.pantheonsorbonne.cri.StuddyBuddyFolder.*;
import fr.pantheonsorbonne.cri.UserFolder.*;

public class MeetingDatabase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private MeetingDatabase() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertMeetingIntoDatabase(String meetingName, Group meetingGroup, LocalDate meetingDate,
            LocalTime meetingStartTime,
            LocalTime meetingEndTime, String meetingLocation, boolean reservationRequired,
            User meetingAdmin) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO Meeting (meeting_name, group_id, meeting_date, meeting_start_time, meeting_end_time, meeting_location, reservation_required, meeting_admin) VALUES (?,?, ?, ?, ?, ?, ?,?)";
            try (PreparedStatement nameCheck = connection
                    .prepareStatement("SELECT COUNT(*) FROM Meeting WHERE meeting_name=? AND group_id=?")) {
                nameCheck.setString(1, meetingName);
                nameCheck.setInt(2, meetingGroup.getGroupId());
                try (ResultSet resultSet = nameCheck.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.println("Meeting name already exists. Please choose another name.");
                        return;
                    }
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, meetingName);
                preparedStatement.setInt(2, meetingGroup.getGroupId());
                preparedStatement.setDate(3, java.sql.Date.valueOf(meetingDate));
                preparedStatement.setTime(4, java.sql.Time.valueOf(meetingStartTime));
                preparedStatement.setTime(5, java.sql.Time.valueOf(meetingEndTime));
                preparedStatement.setString(6, meetingLocation);
                preparedStatement.setBoolean(7, reservationRequired);
                preparedStatement.setInt(8, meetingAdmin.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting meeting into the database: " + e.getMessage());
        }
    }

    public static void removeMeetingFromDatabase(Meeting meeting) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM Meeting WHERE meeting_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, meeting.getMeetingId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting meeting from the database: " + e.getMessage());
        }
    }

    public static Meeting getMeetingByName(String meetingName) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String selectQuery = "SELECT * FROM Meeting WHERE meeting_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, meetingName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Meeting(resultSet.getInt("meeting_id"),
                                resultSet.getString("meeting_name"),
                                GroupDatabase.getGroupById(resultSet.getInt("group_id")),
                                resultSet.getDate("meeting_date").toLocalDate(),
                                resultSet.getTime("meeting_start_time").toLocalTime(),
                                resultSet.getTime("meeting_end_time").toLocalTime(),
                                resultSet.getString("meeting_location"),
                                resultSet.getBoolean("reservation_required"),
                                FindUser.getUserById(resultSet.getInt("meeting_admin")));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding meeting by name from the database: " + e.getMessage());
        }
        return null;
    }

    public static void updateMeetingInDatabase(Meeting meeting) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String updateQuery = "UPDATE Meeting SET meeting_name = ?, group_id = ?, meeting_date = ?, meeting_start_time = ?, meeting_end_time = ?, meeting_location = ?, reservation_required = ?, meeting_admin = ? WHERE meeting_id = ?";
            try (PreparedStatement nameCheck = connection
                    .prepareStatement("SELECT COUNT(*) FROM Meeting WHERE meeting_name=? AND group_id=?")) {
                nameCheck.setString(1, meeting.getMeetingName());
                nameCheck.setInt(2, meeting.getMeetingGroup().getGroupId());
                try (ResultSet resultSet = nameCheck.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.println("Meeting name already exists. Please choose another name.");
                        return;
                    }
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, meeting.getMeetingName());
                preparedStatement.setInt(2, meeting.getMeetingGroup().getGroupId());
                preparedStatement.setDate(3, java.sql.Date.valueOf(meeting.getMeetingDate()));
                preparedStatement.setTime(4, java.sql.Time.valueOf(meeting.getMeetingStartTime()));
                preparedStatement.setTime(5, java.sql.Time.valueOf(meeting.getMeetingEndTime()));
                preparedStatement.setString(6, meeting.getMeetingLocation());
                preparedStatement.setBoolean(7, meeting.isReservationRequired());
                preparedStatement.setInt(8, meeting.getMeetingAdmin().getUserId());
                preparedStatement.setInt(9, meeting.getMeetingId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error updating meeting in the database: " + e.getMessage());
        }
    }

    public static void removeAllMeetingsFromGroup(Group group) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM Meeting WHERE group_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting all meetings from the group in the database: " + e.getMessage());
        }
    }
}
