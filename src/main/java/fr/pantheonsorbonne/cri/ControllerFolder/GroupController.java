package fr.pantheonsorbonne.cri.ControllerFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.GroupFolder.*;

import java.util.Scanner;

public class GroupController {

    private static Scanner scanner = new Scanner(System.in);

    private GroupController() {
        throw new IllegalStateException("Utility class");
    }

    public static void createGroup(User admin) {
        Scanner scanner = new Scanner(System.in);
        // Demander à l'utilisateur de saisir le nom du groupe
        System.out.print("Nom du groupe : ");
        String groupName = scanner.nextLine();

        // Demander à l'utilisateur de saisir le domaine d'étude
        System.out.print("Filière d'étude : ");
        String studyDomain = scanner.nextLine();

        // Demander à l'utilisateur de saisir le niveau d'étude
        System.out.print("Niveau d'étude : ");
        String studyLevel = scanner.nextLine();

        // Créer un groupe
        GroupDatabase.insertGroupIntoDatabase(groupName, studyDomain, studyLevel, admin);
        GroupDatabase.findGroupByGroupname(groupName);
    }

    public static void sendGroupRequest(Group group, User user) {
        AdminRequestDatabase.insertAdminRequestIntoDatabase(group.getGroupAdmin(), user, group);
    }

    public static void answerGroupRequest(Group group, User user) {
        boolean choice = scanner.nextBoolean();
        if (choice) {
            System.out.println(
                    "You accepted " + user.getFirstName() + " " + user.getLastName() + " into " + group.getGroupName()
                            + "!");
            addMember(user, group);
        } else {
            System.out.println("You refused " + user.getFirstName() + " " + user.getLastName() + "'s request to join "
                    + group.getGroupName() + ".");
        }
        AdminRequestDatabase.removeAdminRequestFromDatabase(group.getGroupAdmin(), user, group);
    }

    public static void sendGroupInvitation(User user, Group group) {
        InvitationRequestDatabase.insertGroupInvitationIntoDatabase(group, user);
    }

    public static void answerGroupInvitation(Group group, User user) {
        boolean choice = scanner.nextBoolean();
        if (choice) {
            System.out.println("You joined " + group.getGroupName() + "!");
            addMember(user, group);
        } else {
            System.out.println("You refused the invitation to join " + group.getGroupName() + ".");
        }
        InvitationRequestDatabase.removeGroupInvitationFromDatabase(group, user);
    }

    private static void addMember(User member, Group group) {
        GroupMembersDatabase.insertGroupMemberIntoDatabase(group, member);
    }

    public static void removeMember(User member, Group group) {
        GroupMembersDatabase.removeGroupMemberFromDatabase(group, member);
    }

    public static void leaveGroup(Group group, User user) {
        removeMember(user, group);
    }

    public static void deleteGroup(Group group) {
        GroupMembersDatabase.removeGroupMembers(group);
        GroupDatabase.removeGroupFromDatabase(group);
    }
}
