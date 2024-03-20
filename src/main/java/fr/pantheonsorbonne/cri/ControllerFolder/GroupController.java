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
        GroupDataBase.insertGroupIntoDatabase(groupName, studyDomain, studyLevel, admin);
        FindGroup.getGroupByGroupname(groupName);
    }

    public static void sendGroupRequest(Group group, User user) {
        group.getGroupAdmin().getAdminRequests().get(group).add(user);
    }

    public static void answerGroupRequest(Group group, User user) {
        boolean choice = scanner.nextBoolean();
        if (choice) {
            System.out.println("You joined " + group.getGroupName());
            addMember(user, group);
        } else {
            System.out.println("You refused to join " + group.getGroupName());
        }
        group.getGroupAdmin().getAdminRequests().get(group).remove(user);
    }

    public static void sendGroupInvitation(User user, Group group) {
        user.getGroupRequestList().add(group);
    }

    public static void answerGroupInvitation(Group group, User user) {
        boolean choice = scanner.nextBoolean();
        if (choice) {
            addMember(user, group);
        }
        user.getGroupRequestList().remove(group);
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
        GroupDataBase.removeGroupFromDatabase(group);
    }
}
