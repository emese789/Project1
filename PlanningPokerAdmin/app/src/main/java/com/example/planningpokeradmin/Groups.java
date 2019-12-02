package com.example.planningpokeradmin;

public class Groups {
    private String groupName;
    private String groupCode;

    public Groups()
    {

    }

    public Groups(String groupCode, String groupName) {
        this.groupName = groupName;
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}
