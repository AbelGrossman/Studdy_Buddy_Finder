package fr.pantheonsorbonne.cri.GroupFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;

public class Group {
    private int groupId;
    private String groupName;
    private String studyDomain;
    private String studyLevel;
    private User groupAdmin;

    public Group(int groupId, String groupName, User groupAdmin, String studyDomain, String studyLevel) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAdmin = groupAdmin;
        this.studyDomain = studyDomain;
        this.studyLevel = studyLevel;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public User getGroupAdmin() {
        return this.groupAdmin;
    }

    public String getStudyDomain() {
        return this.studyDomain;
    }

    public String getStudyLevel() {
        return this.studyLevel;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setStudyDomain(String studyDomain) {
        this.studyDomain = studyDomain;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }
}
