package fr.pantheonsorbonne.cri.CasesFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.ControllerFolder.*;
import fr.pantheonsorbonne.cri.StuddyBuddyFolder.*;

import java.util.Scanner;

public class StuddyBuddyCases {

    private StuddyBuddyCases() {
        throw new IllegalStateException("Utility class");
    }

    public static void userSearchCases(User currentUser, Scanner scanner) {
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
                scanner.nextLine();
                switch (userOption) {
                    case 1:
                        System.out
                                .println("You chose to send a Studdy Buddy request.");
                        System.out
                                .println("Enter the username of the user you want to send a Studdy Buddy request to :");
                        if (scanner.hasNextLine()) {
                            username = scanner.nextLine();
                            searchedUser = FindUser.getUserByUsername(username);
                            if (searchedUser == null) {
                                System.out.println("The user doesn't exist.");
                                continue;
                            }
                            if (StuddyBuddyDatabase.findStuddyBuddy(currentUser, searchedUser) != null) {
                                System.out.println("You are already Studdy Buddies with this user.");
                                continue;
                            }
                            if (FriendRequestDatabase.getFriendRequestUserById(searchedUser.getUserId()) != null) {
                                System.out.println("You already sent/received a request to/from this user.");
                                continue;
                            }
                            StuddyBuddiesController.sendStuddyBuddyRequest(currentUser, searchedUser);
                        } else {
                            System.out.println("Invalid input. Please input a string.");
                            scanner.nextLine();
                        }
                        break;
                    case 2:
                        System.out.println("You chose to answer a Studdy Buddy request.");
                        System.out.println("Enter the username of the user you want to answer the request of :");
                        if (scanner.hasNextLine()) {
                            username = scanner.nextLine();
                            searchedUser = FindUser.getUserByUsername(username);
                            if (searchedUser == null) {
                                System.out.println("The user doesn't exist.");
                                continue;
                            }
                            if (StuddyBuddyDatabase.findStuddyBuddy(currentUser, searchedUser) != null) {
                                System.out.println("You are already Studdy Buddies with this user.");
                                continue;
                            }
                            if (FriendRequestDatabase.getFriendRequestUserById(searchedUser.getUserId()) == null) {
                                System.out.println("You don't have any request from this user.");
                                continue;
                            }
                            StuddyBuddiesController.answerStuddyBuddyRequest(currentUser, searchedUser, scanner);
                            FriendRequestDatabase.removeRequestFromDatabase(currentUser, searchedUser);
                        } else {
                            System.out.println("Invalid input. Please input a string.");
                            scanner.nextLine();
                        }
                        break;
                    case 3:
                        System.out.println("You chose to get rid of a Studdy Buddy.");
                        System.out.println(
                                "Enter the username of the user you want to remove from your Studdy Buddies :");
                        if (scanner.hasNextLine()) {
                            username = scanner.nextLine();
                            searchedUser = FindUser.getUserByUsername(username);
                            if (searchedUser == null) {
                                System.out.println("The user doesn't exist");
                                continue;
                            }
                            if (StuddyBuddyDatabase.findStuddyBuddy(currentUser, searchedUser) == null) {
                                System.out.println("The user isn't one of your Studdy Buddies");
                                continue;
                            }
                            StuddyBuddiesController.removeStuddyBuddy(currentUser, searchedUser);
                            StuddyBuddiesController.removeStuddyBuddy(searchedUser, currentUser);
                            System.out.println(searchedUser.getFirstName() + " " + searchedUser.getLastName()
                                    + " isn't your Studdy Buddy anymore");
                        } else {
                            System.out.println("Invalid input. Please input a string.");
                            scanner.nextLine();
                        }
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
                scanner.nextLine();
            }
        }
    }
}
