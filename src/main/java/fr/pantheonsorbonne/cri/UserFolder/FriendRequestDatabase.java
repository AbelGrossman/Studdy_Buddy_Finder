package fr.pantheonsorbonne.cri.UserFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendRequestDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private FriendRequestDatabase() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertFriendRequestIntoDatabase(User sender, User receiver) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO FriendRequest (sender_id, receiver_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, sender.getUserId());
                preparedStatement.setInt(2, receiver.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error inserting request into the database: " + e.getMessage());
        }
    }

    public static void removeRequestFromDatabase(User receiver, User sender) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM FriendRequest WHERE sender_id = ? AND receiver_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, sender.getUserId());
                preparedStatement.setInt(2, receiver.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting request from the database" + e.getMessage());
        }
    }

    public static User getFriendRequestUserById(int userId) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM FriendRequest WHERE sender_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int senderId = resultSet.getInt("sender_id");
                        // Assuming sender_id corresponds to the user_id in the User table
                        String userQuery = "SELECT * FROM User WHERE user_id = ?";
                        try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
                            userStatement.setInt(1, senderId);
                            try (ResultSet userResultSet = userStatement.executeQuery()) {
                                if (userResultSet.next()) {
                                    user = new User(
                                            userResultSet.getInt("user_id"),
                                            userResultSet.getString("first_name"),
                                            userResultSet.getString("last_name"),
                                            userResultSet.getString("user_name"),
                                            userResultSet.getString("user_email"),
                                            userResultSet.getString("user_password"),
                                            userResultSet.getString("location_1"),
                                            userResultSet.getString("location_2"),
                                            userResultSet.getString("interest_1"),
                                            userResultSet.getString("interest_2"),
                                            userResultSet.getString("user_studies"));
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting user from the database: " + e.getMessage());
        }
        return user;
    }
}
