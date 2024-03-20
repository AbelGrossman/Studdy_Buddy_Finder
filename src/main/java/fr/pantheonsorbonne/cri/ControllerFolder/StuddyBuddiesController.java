package fr.pantheonsorbonne.cri.ControllerFolder;

import fr.pantheonsorbonne.cri.StuddyBuddyFolder.*;
import fr.pantheonsorbonne.cri.UserFolder.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class StuddyBuddiesController {

    private static Scanner scanner = new Scanner(System.in);

    private StuddyBuddiesController() {
        throw new IllegalStateException("Utility class");
    }

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
    }

    private static void addStuddyBuddy(User user, User studdyBuddy) {
        StuddyBuddyDatabase.insertStuddyBuddyIntoDatabase(user, studdyBuddy);
    }

    public static void removeStuddyBuddy(User user, User studdyBuddy) {
        StuddyBuddyDatabase.insertStuddyBuddyIntoDatabase(user, studdyBuddy);
        StuddyBuddyDatabase.removeStuddyBuddyFromDatabase(user, studdyBuddy);
    }
}
