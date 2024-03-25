package fr.pantheonsorbonne.cri.GroupFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pantheonsorbonne.cri.StuddyBuddyFolder.FindUser;
import fr.pantheonsorbonne.cri.UserFolder.*;

public class GroupDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private GroupDatabase() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertGroupIntoDatabase(String groupName, String studyDomain, String studyLevel,
            User groupAdmin) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO `Group` (group_name, study_domain, study_level, admin_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement nameCheck = connection
                    .prepareStatement("SELECT COUNT(*) FROM `Group` WHERE group_name=?")) {
                nameCheck.setString(1, groupName);
                try (ResultSet resultSet = nameCheck.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.println("Group name already exists. Please choose another name.");
                        return;
                    }
                }
            }
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
            String deleteQuery = "DELETE FROM `Group` WHERE group_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting group from the database: " + e.getMessage());
        }
    }

    public static Group findGroupByGroupname(String groupname) {
        Group group = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM `Group` WHERE group_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, groupname);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        group = new Group(
                                resultSet.getInt("group_id"),
                                resultSet.getString("group_name"),
                                FindUser.getUserById(resultSet.getInt("admin_id")),
                                resultSet.getString("study_domain"),
                                resultSet.getString("study_level"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting group by groupname: " + e.getMessage());
        }

        return group;
    }

    public static Group getGroupById(int groupId) {
        Group group = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM `Group` WHERE group_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, groupId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        group = new Group(
                                resultSet.getInt("group_id"),
                                resultSet.getString("group_name"),
                                FindUser.getUserById(resultSet.getInt("admin_id")),
                                resultSet.getString("study_domain"),
                                resultSet.getString("study_level"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting group by group id: " + e.getMessage());
        }
        return group;
    }

    public static void updateGroupInDatabase(Group group) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String updateQuery = "UPDATE `Group` SET group_name=?, study_domain=?, study_level=? WHERE group_id=?";
            try (PreparedStatement nameCheck = connection
                    .prepareStatement("SELECT COUNT(*) FROM `Group` WHERE group_name=?")) {
                nameCheck.setString(1, group.getGroupName());
                try (ResultSet resultSet = nameCheck.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.println("Group name already exists. Please choose another name.");
                        return;
                    }
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, group.getGroupName());
                preparedStatement.setString(2, group.getStudyDomain());
                preparedStatement.setString(3, group.getStudyLevel());
                preparedStatement.setInt(4, group.getGroupId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error updating group: " + e.getMessage());
        }
    }
}
