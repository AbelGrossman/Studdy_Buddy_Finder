package fr.pantheonsorbonne.cri.GroupFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pantheonsorbonne.cri.UserFolder.*;

public class AdminRequestDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private AdminRequestDatabase() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertAdminRequestIntoDatabase(User admin, User sender, Group group) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO AdminRequests (admin_id, sender_id, group_id) VALUES (?,?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, admin.getUserId());
                preparedStatement.setInt(2, sender.getUserId());
                preparedStatement.setInt(3, group.getGroupId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting request into the database" + e.getMessage());
        }
    }

    public static void removeAdminRequestFromDatabase(User admin, User sender, Group group) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM AdminRequests WHERE admin_id = ? AND sender_id = ? AND group_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, admin.getUserId());
                preparedStatement.setInt(2, sender.getUserId());
                preparedStatement.setInt(3, group.getGroupId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting request from the database" + e.getMessage());
        }
    }

    public static User findAdminRequest(User admin, User sender, Group group) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String selectQuery = "SELECT * FROM AdminRequests WHERE admin_id = ? AND sender_id = ? AND group_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, admin.getUserId());
                preparedStatement.setInt(2, sender.getUserId());
                preparedStatement.setInt(3, group.getGroupId());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return sender;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding request in the database" + e.getMessage());
        }
        return null;
    }

}
