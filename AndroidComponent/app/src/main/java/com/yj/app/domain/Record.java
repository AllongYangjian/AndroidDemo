package com.yj.app.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "record")
public class Record {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    public String name;

    @ColumnInfo(name = "record_time")
    public Date recordTime;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recordTime=" + recordTime +
                '}';
    }
}
