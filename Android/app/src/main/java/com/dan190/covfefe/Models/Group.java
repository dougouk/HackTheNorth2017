package com.dan190.covfefe.Models;

import android.support.annotation.Nullable;

/**
 * Created by david on 2017-09-16.
 */

public class Group {
    private String groupName;
    private String groupCode;

    public Group(String name){
        this.groupName = name;
    }

    public String getName(){
        return groupName;
    }

    public String setGroupCode(String group_code){
        this.groupCode = group_code;
    }
}
