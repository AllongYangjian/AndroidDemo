package com.yj.app.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 库
 */
@Entity(tableName = "library")
public class Library {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    /**
     * 库名
     */
    public String name;

    /**
     * 学生id
     */
    public Integer sid;

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sid=" + sid +
                '}';
    }
}
