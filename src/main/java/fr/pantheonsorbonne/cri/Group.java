package fr.pantheonsorbonne.cri;

import java.util.List;
import java.util.Random;

public class Group {
    private Random random = new Random();
    private int groupId;
    private String groupName;
    private List<User> members;
    private long adminId;
    private int nbMembers;
    private String studyDomain;
    private String studyLevel;

    public Group(String groupName, List<User> members, long adminId, String studyDomain, String studyLevel) {
        this.groupId = 0;
        this.groupName = groupName;
        this.members = members;
        this.adminId = adminId;
        this.nbMembers = members.size();
        this.studyDomain = studyDomain;
        this.studyLevel = studyLevel;
    }

    public void addMember(User member) {
        this.members.add(member);
        this.nbMembers++;
    }

    public void removeMember(User member) {
        this.members.remove(member);
        this.nbMembers--;
    }

    public List<User> getMembers() {
        return this.members;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public long getGroupId() {
        return this.groupId;
    }

    public long getAdminId() {
        return this.adminId;
    }

    public int getNbMembers() {
        return this.nbMembers;
    }

    public String getStudyDomain() {
        return this.studyDomain;
    }

    public String getStudyLevel() {
        return this.studyLevel;
    }
}
