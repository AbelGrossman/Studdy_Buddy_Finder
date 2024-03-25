package fr.pantheonsorbonne.cri.ControllerFolder;

import fr.pantheonsorbonne.cri.StuddyBuddyFolder.*;
import fr.pantheonsorbonne.cri.UserFolder.*;

import java.util.Scanner;

public class StuddyBuddiesController {

    private StuddyBuddiesController() {
        throw new IllegalStateException("Utility class");
    }

    public static void sendStuddyBuddyRequest(User user, User studdyBuddy) {
        FriendRequestDatabase.insertFriendRequestIntoDatabase(user, studdyBuddy);
    }

    public static void answerStuddyBuddyRequest(User user, User askingUser, Scanner scanner) {
        System.out.println(askingUser.getFirstName() + " " + askingUser.getLastName()
                + " wants to be your Studdy Buddy. Do you accept ? (true/false)");
        boolean choice = scanner.nextBoolean();
        if (choice) {
            System.out.println(
                    askingUser.getUserName() + " is your new Studdy Buddy !");
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
        StuddyBuddyDatabase.removeStuddyBuddyFromDatabase(user, studdyBuddy);
    }
}
