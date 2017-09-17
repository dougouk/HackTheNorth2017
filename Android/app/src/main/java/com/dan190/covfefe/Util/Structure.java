package com.dan190.covfefe.Util;

/**
 * Created by david on 2017-09-16.
 */

public class Structure {
    public static class User {

        public String name;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String name) {
            this.name = name;
        }

    }
}
