package fr.pantheonsorbonne.cri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String interest1;
    private String interest2;
    private String userStudies;
    private List<User> studdyBuddies = new ArrayList<>();
    private List<User> requestList = new ArrayList<>();
    private List<Group> groupRequestList = new ArrayList<>();
    private Map<Group, List<User>> adminRequests = new HashMap<>();
    private GroupController groupManagers;
    private StuddyBuddiesManager studdyBuddiesManager;

    public User(String firstName, String lastName, String userName, String email, String password,
            String location1, String location2, String interest1, String interest2,
            String userStudies) {
        this.userId = currentId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userEmail = email;
        this.userPassword = password;
        this.location1 = location1;
        this.location2 = location2;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.userStudies = userStudies;
    }

    public String getUserStudies() {
        return this.userStudies;
    }

    public void setUserStudies(String userStudies) {
        this.userStudies = userStudies;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getLocation1() {
        return location1;
    }

    public void setLocation1(String location1) {
        this.location1 = location1;
    }

    public String getLocation2() {
        return location2;
    }

    public void setLocation2(String location2) {
        this.location2 = location2;
    }

    public List<User> getStuddyBuddies() {
        return studdyBuddies;
    }

    public String getInterest1() {
        return this.interest1;
    }

    public void setInterest1(String interest1) {
        this.interest1 = interest1;
    }

    public String getInterest2() {
        return this.interest2;
    }

    public void setInterest2(String interest2) {
        this.interest2 = interest2;
    }

    public int getUserId() {
        return this.userId;
    }

    public Map<Group, List<User>> getAdminRequests() {
        return this.adminRequests;
    }

    public List<Group> getGroupRequestList() {
        return this.groupRequestList;
    }

    public List<User> getRequestList() {
        return requestList;
    }

    public GroupController getGroupManagers() {
        return groupManagers;
    }

    public StuddyBuddiesManager getStuddyBuddiesManager() {
        return studdyBuddiesManager;
    }
}
