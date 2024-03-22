package fr.pantheonsorbonne.cri.CasesFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.ControllerFolder.*;
import fr.pantheonsorbonne.cri.GroupFolder.*;
import fr.pantheonsorbonne.cri.StuddyBuddyFolder.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class GroupCases {
    private static Scanner scanner;

    private GroupCases() {
        throw new IllegalStateException("Utility class");
    }

    public static void groupSelectionCases(User currentUser) {
        boolean groupRunning = true;
        while (groupRunning) {
            System.out.println("Select a group by entering its name :");
            scanner = new Scanner(System.in);
            String groupName = scanner.nextLine();
            Group currentGroup = GroupDatabase.findGroupByGroupname(groupName);
            if (currentGroup == null) {
                throw new NoSuchElementException("The group doesn't exist");
            } else if (currentUser.getUserId() != currentGroup.getGroupAdmin().getUserId()) {
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
                        int groupResult = groupUserSelectionCases(currentGroup, currentUser);
                        if (groupResult == 0) {
                            groupRunning = false;
                            scanner.close();
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
                        int groupResult = groupAdminSelectionCases(currentGroup, currentUser);
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

    public static int groupUserSelectionCases(Group currentGroup, User currentUser) {
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
                MeetingCases.viewMeetings(currentGroup, currentUser);
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

    public static int groupAdminSelectionCases(Group currentGroup, User currentUser) {
        int groupOption = scanner.nextInt();
        switch (groupOption) {
            case 1:
                System.out.println("You chose to answer a group request.");
                System.out.println("Enter the username of the user you want to answer the request of :");
                String username1 = scanner.nextLine();
                User chosenUser1 = FindUser.getUserByUsername(username1);
                GroupController.answerGroupRequest(currentGroup, chosenUser1);
                break;
            case 2:
                System.out.println("You chose de send a group invitation");
                System.out.println("Enter the username of the user you want to invite to the group :");
                String username2 = scanner.nextLine();
                User searchedUser = FindUser.getUserByUsername(username2);
                if (searchedUser == null) {
                    throw new NoSuchElementException("The user doesn't exist");
                } else if (GroupMembersDatabase.findGroupMembers(currentGroup, searchedUser) != null) {
                    throw new NoSuchElementException("The user is already in the group");
                } else if (StuddyBuddyDatabase.findStuddyBuddy(currentUser, searchedUser) == null) {
                    throw new NoSuchElementException("The user isn't one of your Studdy Buddies");
                }
                GroupController.sendGroupInvitation(searchedUser, currentGroup);
                break;
            case 3:
                System.out.println("You chose to remove a group member.");
                System.out.println("Enter the username of the user you want to remove from the group :");
                String username3 = scanner.nextLine();
                User chosenUser2 = FindUser.getUserByUsername(username3);
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
