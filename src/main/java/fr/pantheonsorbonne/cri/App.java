package fr.pantheonsorbonne.cri;

import fr.pantheonsorbonne.cri.MeetingFolder.*;
import fr.pantheonsorbonne.cri.ModelFolder.DataBaseConnection;
import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.GroupFolder.*;
import fr.pantheonsorbonne.cri.ControllerFolder.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class App {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean running = true;
    private static User currentUser = null;
    private static boolean databaseCreated = false;

    public static void main(String[] args) {
        if (databaseCreated == false) {
            DataBaseConnection.dataBaseConnect();
            databaseCreated = true;
        }

        System.out.println("Welcome to Studdy Buddy Finder !");
        System.out.println("--------------------------------");

        while (running) {
            // Afficher le menu
            System.out.println("Login Menu:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("0. Leave App");

            // Demander à l'utilisateur de choisir une option
            System.out.println("Please choose an option:");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                // Effectuer une action en fonction du choix de l'utilisateur
                switch (choice) {
                    case 1:
                        // Code pour l'inscription
                        System.out.println("You chose to Sign Up.");
                        UserSignup.signup();
                        break;
                    case 2:
                        // Appeler le main de UserLogin pour la connexion
                        System.out.println("You chose to Login.");
                        currentUser = UserLogin.login();
                        break;
                    case 0:
                        System.out.println("Thank you for using the app! See you soon!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a valid option.");
                        break;
                }
                if (currentUser != null) {
                    loggedInCases();
                }
            } else {
                System.out.println("Invalid input. Please input an integer.");
                scanner.nextLine(); // Pour vider le buffer du scanner
            }
        }

    }

    public static void loggedInCases() {
        boolean runningLogin = true;
        while (runningLogin) {
            System.out.println("Menu: ");
            System.out.println("1. Modify Profile");
            System.out.println("2. Create Group");
            System.out.println("3. Search User");
            System.out.println("4. Select Group");
            System.out.println("5. Logout");
            System.out.println("0. Leave App");

            // Demander à l'utilisateur de choisir une option
            System.out.println("Please choose an option:");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        // Code pour modifier le profil
                        System.out.println("You chose to modifiy your profile.");
                        // Appeler la méthode pour modifier le profil
                        UserController.updateUserProfile(currentUser);
                        break;
                    case 2:
                        // Code pour créer un groupe
                        System.out.println("You chose to create a group.");
                        // Appeler la méthode pour créer un groupe
                        GroupController.createGroup(currentUser);
                        break;
                    case 3:
                        // Code pour rechercher un utilisateur
                        System.out.println("You chose to search a user.");
                        userSearchCases();
                        break;
                    case 4:
                        // Code pour sélectionner un groupe
                        System.out.println("You chose to select a group.");
                        groupSelectionCases();
                        break;
                    case 5:
                        System.out.println("You chose to log out");
                        currentUser = null;
                        runningLogin = false;
                        break;
                    case 0:
                        running = false;
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

    public static void userSearchCases() {
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
                int groupOption = scanner.nextInt();
                switch (groupOption) {
                    case 1:
                        System.out
                                .println("You chose to send a Studdy Buddy request.");
                        username = scanner.nextLine();
                        searchedUser = UserSignup.getRegisteredUserByUsername(username);
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
                        searchedUser = UserSignup.getRegisteredUserByUsername(username);
                        if (searchedUser == null) {
                            throw new NoSuchElementException("The user doesn't exist");
                        } else if (!currentUser.getStuddyBuddies().contains(searchedUser)) {
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

    public static void groupSelectionCases() {
        boolean groupRunning = true;
        while (groupRunning) {
            int groupId = scanner.nextInt();
            Group currentGroup = GroupController.getCreatedGroupById(groupId);
            if (currentGroup == null) {
                throw new NoSuchElementException("The group doesn't exist");
            } else if (currentUser != currentGroup.getGroupAdmin()) {
                while (groupRunning) {
                    System.out.println("Group Menu :");
                    System.out.println("1. Send Group Request");
                    System.out.println("2. Leave Group");
                    System.out.println("3. Schedule Meeting");
                    System.out.println("4. View Meetings");
                    System.out.println("5. Answer Group Invitation");
                    System.out.println("0. Leave User Group Menu");
                    System.out.print("Please choose an option : ");
                    if (scanner.hasNextInt()) {
                        int groupResult = groupUserSelectionCases(currentGroup);
                        if (groupResult == 0) {
                            groupRunning = false;
                        }
                    } else {
                        System.out.println("Invalid input. Please input an integer.");
                        scanner.nextLine(); // Pour vider le buffer du scanner
                    }
                }
            } else {
                while (groupRunning && currentGroup != null) {
                    System.out.println("Group Admin Menu :");
                    System.out.println("1. Answer Group Request");
                    System.out.println("2. Send Group Invitation");
                    System.out.println("3. Remove Group Member");
                    System.out.println("4. Delete Group");
                    System.out.println("5. Schedule Meeting");
                    System.out.println("6. View Meetings");
                    System.out.println("0. Leave Admin Group Menu");
                    System.out.print("Please choose an option : ");
                    if (scanner.hasNextInt()) {
                        int groupResult = groupAdminSelectionCases(currentGroup);
                        if (groupResult == 4) {
                            currentGroup = null;
                        } else if (groupResult == 0) {
                            groupRunning = false;
                        }
                    } else {
                        System.out.println("Invalid input. Please input an integer.");
                        scanner.nextLine(); // Pour vider le buffer du scanner
                    }
                }
            }
        }
    }

    public static int groupUserSelectionCases(Group currentGroup) {
        int groupOption = scanner.nextInt();
        switch (groupOption) {
            case 1:
                System.out.println("You chose to send a group request.");
                GroupController.sendGroupRequest(currentGroup, currentUser);
                break;
            case 2:
                System.out.println("You chose to leave the group.");
                GroupController.leaveGroup(currentGroup, currentUser);
                break;
            case 3:
                System.out.println("You chose to join a meeting.");
                MeetingController.createMeeting(currentUser, currentGroup);
                break;
            case 4:
                System.out.println("You chose to view the meetings.");
                viewMeetings(currentGroup, currentUser);
                break;
            case 5:
                System.out.println("You chose to answer a group invitation.");
                GroupController.answerGroupInvitation(currentGroup, currentUser);
                break;
            case 0:
                System.out.println("Back to the main Menu");
                return 0;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
                break;
        }
        return 4;
    }

    public static void viewMeetings(Group currentGroup, User currentUser) {
        boolean meetingRunning = true;
        while (meetingRunning) {
            int meetingId = scanner.nextInt();
            Meeting currentMeeting = MeetingController.getCreatedMeetingById(meetingId);
            if (currentMeeting == null) {
                throw new NoSuchElementException("The meeting doesn't exist");
            } else if (currentUser != currentMeeting.getMeetingAdmin()) {
                while (meetingRunning) {
                    System.out.println("Meeting Menu :");
                    System.out.println("1. Join Meeting");
                    System.out.println("2. Leave Meeting");
                    System.out.println("0. Back to the Group Menu");
                    System.out.print("Please choose an option : ");
                    if (scanner.hasNextInt()) {
                        int meetingResult = meetingMemberSelectionCases(currentMeeting);
                        if (meetingResult == 0) {
                            meetingRunning = false;
                        }
                    } else {
                        System.out.println("Invalid input. Please input an integer.");
                        scanner.nextLine(); // Pour vider le buffer du scanner
                    }
                }
            } else {
                while (meetingRunning) {
                    System.out.println("Meeting Admin Menu :");
                    System.out.println("1. Remove Meeting");
                    System.out.println("0. Back to the Group Menu");
                    System.out.print("Please choose an option : ");
                    if (scanner.hasNextInt()) {
                        int meetingResult = meetingAdminSelectionCases(currentMeeting);
                        if (meetingResult == 1) {
                            currentMeeting = null;
                        } else if (meetingResult == 0) {
                            meetingRunning = false;
                        }
                    } else {
                        System.out.println("Invalid input. Please input an integer.");
                        scanner.nextLine(); // Pour vider le buffer du scanner
                    }
                }
            }
            System.out.println("Meeting Menu :");
            System.out.println("0. Back to the Group Menu");
            System.out.print("Please choose an option : ");

        }
    }

    public static int meetingMemberSelectionCases(Meeting currentMeeting) {
        int meetingOption = scanner.nextInt();
        switch (meetingOption) {
            case 1:
                System.out.println("You chose to join the meeting.");
                MeetingController.joinMeeting(currentUser, currentMeeting);
                break;
            case 2:
                System.out.println("You chose to leave the meeting.");
                MeetingController.leaveMeeting(currentUser, currentMeeting);
                break;
            case 0:
                System.out.println("Back to the Group Menu");
                return 0;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
                break;
        }
        return 3;
    }

    public static int meetingAdminSelectionCases(Meeting currentMeeting) {
        int meetingOption = scanner.nextInt();
        switch (meetingOption) {
            case 1:
                System.out.println("You chose to remove the meeting.");
                MeetingController.removeMeeting(currentMeeting);
                return 1;
            case 0:
                System.out.println("Back to the Group Menu");
                return 0;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
                break;
        }
        return 2;
    }

    public static int groupAdminSelectionCases(Group currentGroup) {
        int groupOption = scanner.nextInt();
        switch (groupOption) {
            case 1:
                System.out.println("You chose to answer a group request.");
                int maxLength1 = currentUser.getAdminRequests().get(currentGroup).size() - 1;
                System.out.println(
                        "Select a user in your group admin request list by choosing a number bewteen 0 and "
                                + maxLength1);
                int select1 = scanner.nextInt();
                User chosenUser1 = currentUser.getAdminRequests().get(currentGroup).get(select1);
                GroupController.answerGroupRequest(currentGroup, chosenUser1);
                break;
            case 2:
                System.out.println("You chose de send a group invitation");
                String username = scanner.nextLine();
                User searchedUser = UserSignup.getRegisteredUserByUsername(username);
                if (searchedUser == null) {
                    throw new NoSuchElementException("The user doesn't exist");
                } else if (!currentUser.getStuddyBuddies().contains(searchedUser)) {
                    throw new NoSuchElementException("The user isn't one of your Studdy Buddies");
                } else if (currentGroup.getMembers().contains(searchedUser)) {
                    throw new NoSuchElementException("The user is already in the group");
                }
                GroupController.sendGroupInvitation(searchedUser, currentGroup);
                break;
            case 3:
                System.out.println("You chose to remove a group member.");
                int maxLength2 = currentGroup.getMembers().size() - 1;
                System.out.println(
                        "Select a user in your group admin request list by choosing a number bewteen 0 and "
                                + maxLength2);
                int select2 = scanner.nextInt();
                User chosenUser2 = currentGroup.getMembers().get(select2);
                GroupController.removeMember(chosenUser2, currentGroup);
                break;
            case 4:
                System.out.println("You chose to delete the group.");
                GroupController.deleteGroup(currentGroup);
                return 4;
            case 0:
                System.out.println("Back to the main Menu");
                return 0;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
                break;
        }
        return 5;
    }
}
