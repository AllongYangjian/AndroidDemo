package com.yj.app.domain;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    public String name;

    public Integer age;

    public String gender;

    /**
     * 引入其他字段
     */
    @Embedded
    public Address address;
}
