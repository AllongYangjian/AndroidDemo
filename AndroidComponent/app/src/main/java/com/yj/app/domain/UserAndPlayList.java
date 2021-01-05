package com.yj.app.domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserAndPlayList {

    @Embedded
    public User user;

    @Relation(parentColumn = "uid",entityColumn = "uid")
    public List<Playlist> list;

    @Override
    public String toString() {
        return "UserAndPlayList{" +
                "user=" + user +
                ", list=" + list +
                '}';
    }
}
