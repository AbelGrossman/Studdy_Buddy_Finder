package fr.pantheonsorbonne.cri;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class StuddyBuddiesManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private static Scanner scanner = new Scanner(System.in);

    private User user;

    public StuddyBuddiesManager(User user) {
        this.user = user;
    }

    public void sendStuddyBuddyRequest(User studdyBuddy) {
        studdyBuddy.getRequestList().add(this.user);
    }

    public void answerStuddyBuddyRequest(List<User> requestList, int userId) {
        boolean choice = scanner.nextBoolean();
        if (choice) {
            addStuddyBuddy(requestList.get(userId));
            requestList.get(userId).getStuddyBuddiesManager().addStuddyBuddy(this.user);
        }
        this.user.getRequestList().remove(requestList.get(userId));
    }

    private void addStuddyBuddy(User studdyBuddy) {
        this.user.getStuddyBuddies().add(studdyBuddy);
        insertStuddyBuddyIntoDatabase(studdyBuddy);
    }

    public void removeStuddyBuddy(User studdyBuddy) {
        this.user.getStuddyBuddies().remove(studdyBuddy);
        removeStuddyBuddyFromDatabase(studdyBuddy);
    }

    private void insertStuddyBuddyIntoDatabase(User studdyBuddy) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO StuddyBuddy (user_id, studdy_buddy_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, this.user.getUserId());
                preparedStatement.setInt(2, studdyBuddy.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error inserting studdy buddy into the database" + e.getMessage());
        }
    }

    private void removeStuddyBuddyFromDatabase(User studdyBuddy) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM StuddyBuddy WHERE user_id = ? AND studdy_buddy_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, this.user.getUserId());
                preparedStatement.setInt(2, studdyBuddy.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting studdy buddy from the database" + e.getMessage());
        }
    }
}
