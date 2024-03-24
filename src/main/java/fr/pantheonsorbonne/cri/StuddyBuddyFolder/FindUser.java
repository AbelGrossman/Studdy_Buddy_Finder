package fr.pantheonsorbonne.cri.StuddyBuddyFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pantheonsorbonne.cri.UserFolder.User;

public class FindUser {

    private FindUser() {
        throw new IllegalStateException("Utility class");
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/study_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static User getUserById(int userId) {
        return userSelector(null, userId);
    }

    public static User getUserByUsername(String username) {
        return userSelector(username, 0);
    }

    private static User userSelector(String username, int userId) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM User WHERE user_name = ? OR user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User(
                                resultSet.getInt("user_id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("user_name"),
                                resultSet.getString("user_email"),
                                resultSet.getString("user_password"),
                                resultSet.getString("location_1"),
                                resultSet.getString("location_2"),
                                resultSet.getString("interest_1"),
                                resultSet.getString("interest_2"),
                                resultSet.getString("user_studies"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
