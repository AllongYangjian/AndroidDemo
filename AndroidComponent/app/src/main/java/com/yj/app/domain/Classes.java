package com.yj.app.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 班级
 */
@Entity(tableName = "classes")
public class Classes {

    @PrimaryKey(autoGenerate = true)
    public Integer id;
    /**
     * 班级名称
     */
    public String name;


}
