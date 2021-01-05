package com.yj.app.domain;

import androidx.room.Embedded;
import androidx.room.Relation;

public class UserLibrary {
    @Embedded
    public User user;

    @Relation(parentColumn = "uid",entityColumn = "sid")
    public Library library;

    @Override
    public String toString() {
        return "UserLibrary{" +
                "user=" + user +
                ", library=" + library +
                '}';
    }
}
