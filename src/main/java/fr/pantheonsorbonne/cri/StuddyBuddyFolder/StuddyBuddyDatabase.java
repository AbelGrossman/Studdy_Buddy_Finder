package fr.pantheonsorbonne.cri.StuddyBuddyFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pantheonsorbonne.cri.UserFolder.User;

public class StuddyBuddyDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/study_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    private StuddyBuddyDatabase() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertStuddyBuddyIntoDatabase(User user, User studdyBuddy) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO StuddyBuddy (user_id, studdy_buddy_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, user.getUserId());
                preparedStatement.setInt(2, studdyBuddy.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error inserting studdy buddy into the database" + e.getMessage());
        }
    }

    public static void removeStuddyBuddyFromDatabase(User user, User studdyBuddy) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM StuddyBuddy WHERE user_id = ? AND studdy_buddy_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, user.getUserId());
                preparedStatement.setInt(2, studdyBuddy.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting studdy buddy from the database" + e.getMessage());
        }
    }

    public static User findStuddyBuddy(User currentUser, User searchedUser){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String selectQuery = "SELECT * FROM StuddyBuddies WHERE user_id = ? AND studdy_buddy_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, currentUser.getUserId());
                preparedStatement.setInt(2, searchedUser.getUserId());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return searchedUser;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding studdy buddy from the database" + e.getMessage());
        }
        return null;
    }
}
