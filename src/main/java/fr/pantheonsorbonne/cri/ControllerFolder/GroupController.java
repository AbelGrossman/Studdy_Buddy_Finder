package fr.pantheonsorbonne.cri.ControllerFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.GroupFolder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class GroupController {

    private static Scanner scanner = new Scanner(System.in);

    private static List<Group> createdGroups = new ArrayList<>();

    public static void createGroup(User admin) {
        Scanner scanner = new Scanner(System.in);
        // Demander à l'utilisateur de saisir le nom du groupe
        System.out.print("Nom du groupe : ");
        String groupName = scanner.nextLine();

        // Demander à l'utilisateur de saisir le domaine d'étude
        System.out.print("Domaine d'étude : ");
        String studyDomain = scanner.nextLine();

        // Demander à l'utilisateur de saisir le niveau d'étude
        System.out.print("Niveau d'étude : ");
        String studyLevel = scanner.nextLine();

        // Créer un groupe
        Group group = new Group(groupName, admin, studyDomain, studyLevel);
        addCreatedGroup(group);
        addMember(admin, group);
    }

    public static void addCreatedGroup(Group group) {
        createdGroups.add(group);
    }

    public static Group getCreatedGroupById(int groupId) {
        for (Group group : createdGroups) {
            if (group.getGroupId() == groupId) {
                return group;
            }
        }
        return null;
    }

    public static List<Group> getCreatedGroups() {
        return createdGroups;
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
        group.getMembers().add(member);
        GroupMembersDatabase.insertGroupMemberIntoDatabase(group, member);
    }

    public static void removeMember(User member, Group group) {
        group.getMembers().remove(member);
        GroupMembersDatabase.removeGroupMemberFromDatabase(group, member);
    }

    public static void leaveGroup(Group group, User user) {
        removeMember(user, group);
    }

    public static void deleteGroup(Group group) {
        GroupMembersDatabase.removeGroupMembers(group);
        getCreatedGroups().remove(group);
        group.removeGroupFromDatabase();
    }
}
