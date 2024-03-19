package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Meeting {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private static int currentId = 0;
    private int meetingId;
    private Group meetingGroup;
    private LocalDate meetingDate;
    private LocalTime meetingStartTime;
    private LocalTime meetingEndTime;
    private String meetingLocation;
    private List<User> participants = new ArrayList<>();
    private int amountOfParticipants;
    private boolean reservationRequired;
    private User meetingAdmin;

    public Meeting(Group meetingGroup, LocalDate meetingDate, LocalTime meetingStartTime, LocalTime meetingEndTime,
            String meetingLocation,
            int amountOfParticipants, boolean reservationRequired, User meetingAdmin) {
        this.meetingId = currentId++;
        this.meetingGroup = meetingGroup;
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.meetingLocation = meetingLocation;
        this.participants.add(meetingAdmin);
        this.amountOfParticipants = amountOfParticipants;
        this.reservationRequired = reservationRequired;
        this.meetingAdmin = meetingAdmin;
        insertMeetingIntoDatabase();
    }

    private void insertMeetingIntoDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO Meeting (meeting_group, meeting_date, meeting_start_time, meeting_end_time, meeting_location, amount_of_participants, reservation_required, meeting_admin) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, this.meetingGroup.getGroupId());
                preparedStatement.setDate(2, java.sql.Date.valueOf(this.meetingDate));
                preparedStatement.setTime(3, java.sql.Time.valueOf(this.meetingStartTime));
                preparedStatement.setTime(4, java.sql.Time.valueOf(this.meetingEndTime));
                preparedStatement.setString(5, this.meetingLocation);
                preparedStatement.setInt(6, this.amountOfParticipants);
                preparedStatement.setBoolean(7, this.reservationRequired);
                preparedStatement.setInt(8, this.meetingAdmin.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting meeting into the database" + e.getMessage());
        }
    }

    public void removeMeetingFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM Meeting WHERE meeting_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, this.meetingId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting meeting from the database" + e.getMessage());
        }
    }

    public int getMeetingId() {
        return meetingId;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public LocalTime getMeetingStartTime() {
        return meetingStartTime;
    }

    public LocalTime getMeetingEndTime() {
        return meetingEndTime;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public int getAmountOfParticipants() {
        return amountOfParticipants;
    }

    public boolean isReservationRequired() {
        return reservationRequired;
    }

    public User getMeetingAdmin() {
        return meetingAdmin;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public Group getMeetingGroup() {
        return meetingGroup;
    }
}
