package fr.pantheonsorbonne.cri.CasesFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.ControllerFolder.*;
import fr.pantheonsorbonne.cri.StuddyBuddyFolder.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class StuddyBuddyCases {
    private static Scanner scanner = new Scanner(System.in);

    private StuddyBuddyCases() {
        throw new IllegalStateException("Utility class");
    }

    public static void userSearchCases(User currentUser) {
        String username = null;
        User searchedUser = null;
        boolean userRunning = true;
        while (userRunning) {
            username = null;
            searchedUser = null;
            System.out.println("Studdy Buddies Menu :");
            System.out.println("1. Send Studdy Buddy Request");
            System.out.println("2. Answer Studdy Buddy Request");
            System.out.println("3. Remove Studdy Buddy");
            System.out.println("0. Quit Studdy Buddies Menu");
            System.out.print("Please choose an option : ");
            if (scanner.hasNextInt()) {
                int userOption = scanner.nextInt();
                switch (userOption) {
                    case 1:
                        System.out
                                .println("You chose to send a Studdy Buddy request.");
                        System.out
                                .println("Enter the username of the user you want to send a Studdy Buddy request to :");
                        username = scanner.nextLine();
                        searchedUser = FindUser.getUserByUsername(username);
                        if (searchedUser == null) {
                            throw new NoSuchElementException("The user doesn't exist.");
                        }
                        StuddyBuddiesController.sendStuddyBuddyRequest(currentUser, searchedUser);
                        break;
                    case 2:
                        System.out.println(
                                "You chose to answer a Studdy Buddy request.");
                        int maxLength = currentUser.getRequestList().size() - 1;
                        System.out.println(
                                "Select a user in your request list by choosing a number bewteen 0 and " + maxLength);
                        int select = scanner.nextInt();
                        StuddyBuddiesController.answerStuddyBuddyRequest(currentUser,
                                currentUser.getRequestList().get(select));
                        break;
                    case 3:
                        System.out.println("You chose to get rid of a Studdy Buddy.");
                        username = scanner.nextLine();
                        searchedUser = FindUser.getUserByUsername(username);
                        if (searchedUser == null) {
                            throw new NoSuchElementException("The user doesn't exist");
                        } else if (StuddyBuddyDatabase.findStuddyBuddy(currentUser, searchedUser) == null) {
                            throw new NoSuchElementException("The user isn't one of your Studdy Buddies");
                        }
                        StuddyBuddiesController.removeStuddyBuddy(currentUser, searchedUser);
                        System.out.println(searchedUser.getFirstName() + " " + searchedUser.getLastName()
                                + " isn't your Studdy Buddy anymore");
                        break;
                    case 0:
                        System.out.println("Back to the main Menu");
                        userRunning = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a valid option.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please input an integer.");
                scanner.nextLine(); // Pour vider le buffer du scanner
            }
        }
    }
}
