package fr.pantheonsorbonne.cri.CasesFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.ControllerFolder.*;
import fr.pantheonsorbonne.cri.GroupFolder.*;
import fr.pantheonsorbonne.cri.StuddyBuddyFolder.*;

import java.util.Scanner;

public class GroupCases {

    private GroupCases() {
        throw new IllegalStateException("Utility class");
    }

    public static void groupSelectionCases(User currentUser, Scanner scanner) {
        boolean groupRunning = true;
        selectingGroupLoop: while (groupRunning) {
            System.out.println("Select a group by entering its name :");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
                String groupName = scanner.nextLine();
                Group currentGroup = GroupDatabase.findGroupByGroupname(groupName);
                if (currentGroup == null) {
                    System.out.println("The group doesn't exist");
                    System.out.println("Do you want to leave the group menu? (true/false)");
                    if (scanner.hasNextBoolean()) {
                        boolean choice = scanner.nextBoolean();
                        if (choice) {
                            break;
                        }
                        continue;
                    } else {
                        System.out.println("Invalid input.");
                        break;
                    }
                } else if (currentUser.getUserId() != currentGroup.getGroupAdmin().getUserId()) {
                    while (groupRunning) {
                        System.out.println("Group Menu :");
                        System.out.println("1. Send Group Request");
                        System.out.println("2. Leave Group");
                        System.out.println("3. Schedule Meeting");
                        System.out.println("4. View Meetings");
                        System.out.println("5. Answer Group Invitation");
                        System.out.println("0. Leave User Group Menu");
                        System.out.println("Please choose an option : ");
                        if (scanner.hasNextInt()) {
                            int groupResult = groupUserSelectionCases(currentGroup, currentUser, scanner);
                            if (groupResult == 0) {
                                groupRunning = false;
                            }
                        } else {
                            scanner.nextLine(); // Pour vider le buffer du scanner
                            System.out.println("Invalid input. Please input an integer.");
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
                        System.out.println("7. Modify Group");
                        System.out.println("0. Leave Admin Group Menu");
                        System.out.print("Please choose an option : ");
                        if (scanner.hasNextInt()) {
                            int groupResult = groupAdminSelectionCases(currentGroup, currentUser, scanner);
                            if (groupResult == 4 || groupResult == 0) {
                                break selectingGroupLoop;
                            }
                        } else {
                            System.out.println("Invalid input. Please input an integer.");
                            scanner.nextLine(); // Pour vider le buffer du scanner
                        }
                    }
                }
            } else {
                System.out.println("Invalid input. Please input a group name.");
            }
        }
    }

    public static int groupUserSelectionCases(Group currentGroup, User currentUser, Scanner scanner) {
        int groupOption = scanner.nextInt();
        switch (groupOption) {
            case 1:
                System.out.println("You chose to send a group request.");
                if (GroupMembersDatabase.findGroupMembers(currentGroup, currentUser) != null) {
                    System.out.println("You are already in the group");
                } else if (AdminRequestDatabase.findAdminRequest(currentGroup.getGroupAdmin(), currentUser,
                        currentGroup) != null) {
                    System.out.println("You already sent a request to join the group");
                } else if (InvitationRequestDatabase.findGroupInvitation(currentGroup, currentUser) != null) {
                    System.out.println("You already received an invitation to join the group");
                } else {
                    GroupController.sendGroupRequest(currentGroup, currentUser);
                }
                break;
            case 2:
                System.out.println("You chose to leave the group.");
                if (GroupMembersDatabase.findGroupMembers(currentGroup, currentUser) == null) {
                    System.out.println("You are not in the group");
                } else {
                    GroupController.leaveGroup(currentGroup, currentUser);
                }
                break;
            case 3:
                if (GroupMembersDatabase.findGroupMembers(currentGroup, currentUser) == null) {
                    System.out.println("You are not in the group");
                } else {
                    System.out.println("You chose to schedule a meeting.");
                    MeetingController.createMeeting(currentUser, currentGroup, scanner);
                }
                break;
            case 4:
                if (GroupMembersDatabase.findGroupMembers(currentGroup, currentUser) == null) {
                    System.out.println("You are not in the group");
                } else {
                    System.out.println("You chose to view the meetings.");
                    MeetingCases.viewMeetings(currentGroup, currentUser, scanner);
                }
                break;
            case 5:
                System.out.println("You chose to answer a group invitation.");
                if (GroupMembersDatabase.findGroupMembers(currentGroup, currentUser) != null) {
                    System.out.println("You are already in the group");
                } else if (InvitationRequestDatabase.findGroupInvitation(currentGroup, currentUser) == null) {
                    System.out.println("You didn't receive any invitation to join the group");
                } else {
                    GroupController.answerGroupInvitation(currentGroup, currentUser, scanner);
                }
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

    public static int groupAdminSelectionCases(Group currentGroup, User currentUser, Scanner scanner) {
        int groupOption = scanner.nextInt();
        switch (groupOption) {
            case 1:
                System.out.println("You chose to answer a group request.");
                System.out.println("Enter the username of the user you want to answer the request of :");
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                    String username1 = scanner.nextLine();
                    User chosenUser1 = FindUser.getUserByUsername(username1);
                    if (chosenUser1 == null) {
                        System.out.println("The user doesn't exist");
                    } else if (AdminRequestDatabase.findAdminRequest(currentUser, chosenUser1, currentGroup) == null) {
                        System.out.println("The user didn't send a request to join the group");
                    } else if (GroupMembersDatabase.findGroupMembers(currentGroup, chosenUser1) != null) {
                        System.out.println("The user is already in the group");
                    } else {
                        GroupController.answerGroupRequest(currentGroup, chosenUser1, scanner);
                    }
                } else {
                    System.out.println("Invalid input. Please input a username.");
                    scanner.nextLine();
                }
                break;
            case 2:
                System.out.println("You chose de send a group invitation");
                System.out.println("Enter the username of the user you want to invite to the group :");
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                    String username2 = scanner.nextLine();
                    User searchedUser = FindUser.getUserByUsername(username2);
                    if (searchedUser == null) {
                        System.out.println("The user doesn't exist");
                    } else if (GroupMembersDatabase.findGroupMembers(currentGroup, searchedUser) != null) {
                        System.out.println("The user is already in the group");
                    } else if (searchedUser.getUserId() == currentUser.getUserId()) {
                        System.out.println("You can't invite yourself to the group");
                    } else if (StuddyBuddyDatabase.findStuddyBuddy(currentUser, searchedUser) == null) {
                        System.out.println("The user isn't one of your Studdy Buddies");
                    } 
                    else if(InvitationRequestDatabase.findGroupInvitation(currentGroup, searchedUser)!=null){
                        System.out.println("The user already received an invitation to join the group");
                    }
                    else if(AdminRequestDatabase.findAdminRequest(currentUser, searchedUser, currentGroup)!=null){
                        System.out.println("The user already sent a request to join the group");
                    }
                    else {
                        GroupController.sendGroupInvitation(searchedUser, currentGroup);
                    }
                } else {
                    System.out.println("Invalid input. Please input a username.");
                    scanner.nextLine();
                }
                break;
            case 3:
                System.out.println("You chose to remove a group member.");
                System.out.println("Enter the username of the user you want to remove from the group :");
                scanner.nextLine();
                if (scanner.hasNextLine()) {
                    String username3 = scanner.nextLine();
                    User chosenUser2 = FindUser.getUserByUsername(username3);
                    if (chosenUser2 == null) {
                        System.out.println("The user doesn't exist");
                    } else if (GroupMembersDatabase.findGroupMembers(currentGroup, chosenUser2) == null) {
                        System.out.println("The user is not in the group");
                    } else if (currentUser.getUserId() == chosenUser2.getUserId()) {
                        System.out.println("You can't remove yourself from the group");
                    } else {
                        GroupController.removeMember(chosenUser2, currentGroup);
                    }
                } else {
                    System.out.println("Invalid input. Please input a username.");
                    scanner.nextLine();
                }
                break;
            case 4:
                System.out.println("You chose to delete the group.");
                System.out.println("Are you sure you want to delete the group? (true/false)");
                if (scanner.hasNextBoolean()) {
                    boolean choice = scanner.nextBoolean();
                    if (choice) {
                        GroupController.deleteGroup(currentGroup);
                        return 4;
                    }
                    System.out.println("You chose not to delete the group.");
                } else {
                    System.out.println("Invalid input. Group not deleted.");
                    scanner.nextLine();
                }
                break;
            case 5:
                System.out.println("You chose to schedule a meeting.");
                MeetingController.createMeeting(currentUser, currentGroup, scanner);
                break;
            case 6:
                System.out.println("You chose to view the meetings.");
                MeetingCases.viewMeetings(currentGroup, currentUser, scanner);
                break;
            case 7:
                System.out.println("You chose to modify the group.");
                GroupController.updateGroup(currentGroup, scanner);
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
