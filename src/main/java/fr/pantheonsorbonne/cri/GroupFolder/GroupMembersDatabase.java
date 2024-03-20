package fr.pantheonsorbonne.cri.GroupFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMembersDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private GroupMembersDatabase() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertGroupMemberIntoDatabase(Group group, User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO GroupMembers (group_id, user_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.setInt(2, user.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting group member into the database: " + e.getMessage());
        }
        System.out.println("Member added successfully!");
    }

    public static void removeGroupMemberFromDatabase(Group group, User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM GroupMembers WHERE group_id = ? AND user_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.setInt(2, user.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting group member from the database: " + e.getMessage());
        }
    }

    public static void removeGroupMembers(Group group) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM GroupMembers WHERE group_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting group members from the database: " + e.getMessage());
        }
    }

    public static User findGroupMembers(Group group, User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM GroupMembers WHERE group_id = ? AND user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.setInt(2, user.getUserId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding group members: " + e.getMessage());
        }
        return null;
    }
}
