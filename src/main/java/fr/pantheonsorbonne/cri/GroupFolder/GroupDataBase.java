package fr.pantheonsorbonne.cri.GroupFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.pantheonsorbonne.cri.UserFolder.*;

public class GroupDataBase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private GroupDataBase() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertGroupIntoDatabase(String groupName, String studyDomain, String studyLevel,
            User groupAdmin) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO `Group` (group_name, study_domain, study_level, admin_id) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, groupName);
                preparedStatement.setString(2, studyDomain);
                preparedStatement.setString(3, studyLevel);
                preparedStatement.setInt(4, groupAdmin.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting group into the database: " + e.getMessage());
        }
        System.out.println("Group created successfully!");
    }

    public static void removeGroupFromDatabase(Group group) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM Group WHERE group_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting group from the database" + e.getMessage());
        }
    }

}
