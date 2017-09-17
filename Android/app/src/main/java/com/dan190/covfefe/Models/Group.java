package com.dan190.covfefe.Models;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by david on 2017-09-16.
 */

public class Group {
    private String groupName;
    private String groupCode;
    private long lastCoffee;
    private List<String> members;

    public Group(String name){
        this.groupName = name;
    }

    public String getName(){
        return groupName;
    }

    public void setGroupCode(String group_code){
        this.groupCode = group_code;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public long getLastCoffee() {
        return lastCoffee;
    }

    public void setLastCoffee(long lastCoffee) {
        this.lastCoffee = lastCoffee;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
