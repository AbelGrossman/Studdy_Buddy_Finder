package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class GroupManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private static Scanner scanner = new Scanner(System.in);

    public GroupManager(User user) {
        String groupName = scanner.nextLine();
        String studyDomain = scanner.nextLine();
        String studyLevel = scanner.nextLine();
        Group group = new Group(groupName, user, studyDomain, studyLevel);
        insertGroupMemberIntoDatabase(group, user);
    }

    private void insertGroupMemberIntoDatabase(Group group, User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO GroupMembers (group_id, user_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.setInt(2, user.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting group member into the database: " + e.getMessage());
        }
    }

    private void removeGroupMemberFromDatabase(Group group, User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM GroupMembers WHERE group_id = ? AND user_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.setInt(2, user.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting group member from the database: " + e.getMessage());
        }
    }

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

    public void addMember(User member, Group group) {
        group.getMembers().add(member);
        insertGroupMemberIntoDatabase(group, member);
    }

    public void removeMember(User member, Group group) {
        group.getMembers().remove(member);
        removeGroupMemberFromDatabase(group, member);
    }

    public void leaveGroup(Group group, User user) {
        removeMember(user, group);
    }

    public void deleteGroup(Group group) {
        deleteGroupMembers(group);
        group.removeGroupFromDatabase();
        group = null;
    }

    private void deleteGroupMembers(Group group) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM GroupMembers WHERE group_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, group.getGroupId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting group members from the database: " + e.getMessage());
        }
    }
}
