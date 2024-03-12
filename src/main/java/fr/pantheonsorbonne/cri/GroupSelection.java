package fr.pantheonsorbonne.cri;

import java.util.Scanner;

public abstract class GroupSelection {

    private static Scanner scanner = new Scanner(System.in);

    public static void sendGroupRequest(Group group, User user) {
        group.getAdmin().getAdminRequests().get(group).add(user);
    }

    public static void answerGroupRequest(Group group, User user) {
        boolean choice = scanner.nextBoolean();
        if (choice) {
            System.out.println("You joined "+group.getGroupName());
            addMember(user, group);
        }
        else{
            System.out.println("You refused to join "+group.getGroupName());
        }
        group.getAdmin().getAdminRequests().get(group).remove(user);
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
        GroupMembersDatabase.deleteGroupMembers(group);
        group.removeGroupFromDatabase();
    }
}
