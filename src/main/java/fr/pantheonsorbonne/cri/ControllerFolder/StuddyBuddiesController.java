package fr.pantheonsorbonne.cri.ControllerFolder;

import fr.pantheonsorbonne.cri.UserFolder.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class StuddyBuddiesController {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private static Scanner scanner = new Scanner(System.in);

    public static void sendStuddyBuddyRequest(User user, User studdyBuddy) {
        studdyBuddy.getRequestList().add(user);
    }

    public static void answerStuddyBuddyRequest(User user, User askingUser) {
        boolean choice = scanner.nextBoolean();
        if (choice) {
            System.out.println(
                    askingUser.getFirstName() + " " + askingUser.getLastName() + " is your new Studdy Buddy !");
            addStuddyBuddy(user, askingUser);
            addStuddyBuddy(askingUser, user);
        } else {
            System.out.println("You refused " + askingUser.getFirstName() + " " + askingUser.getLastName()
                    + "'s Studdy Buddy request.");
        }
        user.getRequestList().remove(askingUser);
    }

    private static void addStuddyBuddy(User user, User studdyBuddy) {
        user.getStuddyBuddies().add(studdyBuddy);
        insertStuddyBuddyIntoDatabase(user, studdyBuddy);
    }

    public static void removeStuddyBuddy(User user, User studdyBuddy) {
        user.getStuddyBuddies().remove(studdyBuddy);
        removeStuddyBuddyFromDatabase(user, studdyBuddy);
    }

    private static void insertStuddyBuddyIntoDatabase(User user, User studdyBuddy) {
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

    private static void removeStuddyBuddyFromDatabase(User user, User studdyBuddy) {
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
}
