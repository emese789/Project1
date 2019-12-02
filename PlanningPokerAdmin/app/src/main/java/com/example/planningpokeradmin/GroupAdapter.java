package com.example.planningpokeradmin;

import android.content.Context;

import java.security.acl.Group;
import java.util.List;

public class GroupAdapter {

    private Context Context;
    private List<Group> GroupList;

    public GroupAdapter(android.content.Context context, List<Group> groupList) {
        this.Context = context;
        this.GroupList = groupList;
    }

}
