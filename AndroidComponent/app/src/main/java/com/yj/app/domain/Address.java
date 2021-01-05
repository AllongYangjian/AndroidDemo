package com.yj.app.domain;

import androidx.room.ColumnInfo;

/**
 * 该类作为其他实体类抽象出来的公有字段
 */
public class Address {

    public String street;

    public String state;

    public String city;

    @ColumnInfo(name = "post_code")
    public Integer postCode;
}
