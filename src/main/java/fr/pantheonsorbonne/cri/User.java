package fr.pantheonsorbonne.cri;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class User {
    private static int currentId = 0;
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String location1;
    private String location2;
    private String location3;
    private String interest1;
    private String interest2;
    private String interest3;
    private String userStudies;
    private List<User> studdyBuddies;

    public User(String firstName, String lastName, String userName, String email, String password,
            String location1, String location2, String location3, String interest1, String interest2, String interest3,
            String studyLevel) {
        this.userId = currentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userEmail = email;
        this.userPassword = password;
        this.location1 = location1;
        this.location2 = location2;
        this.location3 = location3;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
        this.userStudies = studyLevel;
        insertUserIntoDatabase();
    }

    private void insertUserIntoDatabase() {
        String url = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String insertQuery = "INSERT INTO User (first_name, last_name, user_name, email_address, password, location, interests, study_level) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, this.firstName);
                preparedStatement.setString(2, this.lastName);
                preparedStatement.setString(3, this.userName);
                preparedStatement.setString(4, this.userEmail);
                preparedStatement.setString(5, this.userPassword);
                // Assuming locations, interests, and studiesLevels are stored as strings for
                // simplicity
                preparedStatement.setString(6, String.join(",", this.location1, this.location2, this.location3));
                preparedStatement.setString(7, String.join(",", this.interest1, this.interest2, this.interest3));
                preparedStatement.setString(8, String.join(",", this.userStudies));
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error inserting user into the database: " + e.getMessage());
        }
    }

    public void joinGroup(Group group) {
        group.addMember(this);
    }

    public void leaveGroup(Group group) {
        group.removeMember(this);
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    public String getUserStudies() {
        return this.userStudies;
    }

    public void addStuddyBuddy(User studdyBuddy) {
        this.studdyBuddies.add(studdyBuddy);
    }

    public void removeStuddyBuddy(User studdyBuddy) {
        this.studdyBuddies.remove(studdyBuddy);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getLocation1() {
        return location1;
    }

    public String getLocation2() {
        return location2;
    }

    public String getLocation3() {
        return location3;
    }

    public List<User> getStuddyBuddies() {
        return studdyBuddies;
    }

    public String getInterest1() {
        return this.interest1;
    }

    public String getInterest2() {
        return this.interest2;
    }

    public String getInterest3() {
        return this.interest3;
    }

    public int getUserId() {
        return userId;
    }
}
