package fr.pantheonsorbonne.cri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class User {
    private static Scanner scanner = new Scanner(System.in);
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

    public void createNewGroup() {
        new GroupManager(this);
    }

    public void setDbUsername(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    public void addStuddyBuddy(User studdyBuddy) {
        this.studdyBuddies.add(studdyBuddy);
    }

    public void removeStuddyBuddy(User studdyBuddy) {
        this.studdyBuddies.remove(studdyBuddy);
    }

    public void sendStuddyBuddyRequest(User studdyBuddy) {
        studdyBuddy.requestList.add(this);
    }

    public void answerStuddyBuddyRequest(List<User> requestList, int userId) {
        boolean choice = scanner.nextBoolean();
        if (choice) {
            this.addStuddyBuddy(requestList.get(userId));
            requestList.get(userId).addStuddyBuddy(this);
        }
        this.requestList.remove(requestList.get(userId));
    }

    public String getUserStudies() {
        return this.userStudies;
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

    public List<User> getStuddyBuddies() {
        return studdyBuddies;
    }

    public String getInterest1() {
        return this.interest1;
    }

    public String getInterest2() {
        return this.interest2;
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
}
