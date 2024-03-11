package fr.pantheonsorbonne.cri;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Group {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private static int currentId = 0;
    private int groupId;
    private String groupName;
    private String studyDomain;
    private String studyLevel;
    private User admin;
    private List<User> members = new ArrayList<>();
    private int nbMembers = members.size();

    public Group(String groupName, User admin, String studyDomain, String studyLevel) {
        this.groupId = currentId++;
        this.groupName = groupName;
        this.admin = admin;
        this.studyDomain = studyDomain;
        this.studyLevel = studyLevel;
        insertGroupIntoDatabase();
    }

    private void insertGroupIntoDatabase() {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO Group (group_name, study_domain, admin_id) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, this.groupName);
                preparedStatement.setString(2, this.studyDomain);
                preparedStatement.setInt(3, this.admin.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error inserting group into the database" + e.getMessage());
        }
    }

    public void removeGroupFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM Group WHERE group_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, this.groupId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting group from the database" + e.getMessage());
        }
    }

    public List<User> getMembers() {
        return this.members;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public User getAdmin() {
        return this.admin;
    }

    public int getNbMembers() {
        return this.nbMembers;
    }

    public void setNbMembers(int nbMembers) {
        this.nbMembers = nbMembers;
    }

    public String getStudyDomain() {
        return this.studyDomain;
    }

    public String getStudyLevel() {
        return this.studyLevel;
    }
}
