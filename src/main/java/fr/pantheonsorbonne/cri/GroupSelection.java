package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class GroupSelection {

    private static Scanner scanner = new Scanner(System.in);

    public void sendGroupRequest(Group group, User user) {
        group.getAdmin().getAdminRequests().get(group).add(user);
    }

    public void answerGroupRequest(Group group, User user) {
        boolean choice = scanner.nextBoolean();
        if (choice) {
            addMember(user, group);
        }
        group.getAdmin().getAdminRequests().get(group).remove(user);
    }

    public void sendGroupInvitation(User user, Group group) {
        user.getGroupRequestList().add(group);
    }

    public void answerGroupInvitation(Group group, User user) {
        boolean choice = scanner.nextBoolean();
        if (choice) {
            addMember(user, group);
        }
        user.getGroupRequestList().remove(group);
    }

    private void addMember(User member, Group group) {
        group.getMembers().add(member);
        GroupMembersDatabase.insertGroupMemberIntoDatabase(group, member);
    }

    private void removeMember(User member, Group group) {
        group.getMembers().remove(member);
        GroupMembersDatabase.removeGroupMemberFromDatabase(group, member);
    }

    public void leaveGroup(Group group, User user) {
        removeMember(user, group);
    }

    public void deleteGroup(Group group) {
        GroupMembersDatabase.deleteGroupMembers(group);
        group.removeGroupFromDatabase();
        group = null;
    }
}
