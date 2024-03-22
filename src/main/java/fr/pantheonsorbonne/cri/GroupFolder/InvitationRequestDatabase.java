package fr.pantheonsorbonne.cri.GroupFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pantheonsorbonne.cri.UserFolder.*;

public class InvitationRequestDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private InvitationRequestDatabase() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertGroupInvitationIntoDatabase(Group group, User receiver) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO InvitationRequestList (group_id, receiver_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.setInt(2, receiver.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error inserting request into the database" + e.getMessage());
        }
    }

    public static void removeGroupInvitationFromDatabase(Group group, User receiver) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM InvitationRequestList WHERE group_id = ? AND receiver_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.setInt(2, receiver.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting request from the database" + e.getMessage());
        }
    }
}
