package fr.pantheonsorbonne.cri;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class User {
    private Random random = new Random();
    private static final List<Long> userAvailableIds = new IdGenerator().getIds();
    private long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private List<String> locations;
    private List<User> studdyBuddies;
    private List<String> interests;
    private Map<String, String> studiesLevels;

    public User(String firstName, String lastName, String userName, String email, String password,
            List<String> locations, List<String> interests, Map<String, String> studiesLevels) {
        this.userId = userAvailableIds.get(random.nextInt(userAvailableIds.size()));
        userAvailableIds.remove(userId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.locations = locations;
        this.interests = interests;
        this.studiesLevels = studiesLevels;
    }

    public void joinGroup(Group group) {
        group.addMember(this);
    }

    public void leaveGroup(Group group) {
        group.removeMember(this);
    }

    public void changeUsername(String userName) {
        this.userName = userName;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void addLocation(String location) {
        this.locations.add(location);
    }

    public void removeLocation(String location) {
        this.locations.remove(location);
    }

    public void addInterest(String interest) {
        this.interests.add(interest);
    }

    public void removeInterest(String interest) {
        this.interests.remove(interest);
    }

    public void addStudyLevel(String study, String level) {
        this.studiesLevels.put(study, level);
    }

    public void removeStudyLevel(String study) {
        this.studiesLevels.remove(study);
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getLocations() {
        return locations;
    }

    public List<User> getStuddyBuddies() {
        return studdyBuddies;
    }

    public List<String> getInterests() {
        return interests;
    }

    public Map<String, String> getStudiesLevels() {
        return studiesLevels;
    }
}
