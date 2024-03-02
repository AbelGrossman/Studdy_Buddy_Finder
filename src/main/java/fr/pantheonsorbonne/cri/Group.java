package fr.pantheonsorbonne.cri;

import java.util.List;
import java.util.Random;

public class Group {
    private Random random = new Random();
    private static final List<Long> groupAvailableIds = new IdGenerator().getIds();
    private long groupId;
    private String groupName;
    private List<User> members;

    public Group(String groupName, List<User> members) {
        this.groupId = groupAvailableIds.get(random.nextInt(groupAvailableIds.size()));
        groupAvailableIds.remove(groupId);
        this.groupName = groupName;
        this.members = members;
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

}
