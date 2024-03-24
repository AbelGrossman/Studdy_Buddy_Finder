package fr.pantheonsorbonne.cri.GroupFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pantheonsorbonne.cri.StuddyBuddyFolder.*;

public class FindGroup {

    private FindGroup() {
        throw new IllegalStateException("Utility class");
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/study_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static Group getGroupByGroupname(String groupname) {
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
}
