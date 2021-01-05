package com.yj.app.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "play_list")
public class Playlist {

    @PrimaryKey
    public Integer id;

    public Integer uid;

    @ColumnInfo(name = "play_name")
    public String playName;

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", uid=" + uid +
                ", playName='" + playName + '\'' +
                '}';
    }
}
