package com.allong.databases.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.allong.databases.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from user where id in (:ids)")
    List<User> getAll(int[] ids);

    @Query("select * from user")
    List<User> getAll();

    @Query("select * from user where first_name like :first and last_name like :last limit 1")
    User findByName(String first,String last);

    @Insert
    void saveUser(User... users);

    @Delete
    void deleteUser(User user);

    @Query("delete from user where id in (:ids)")
    void deleteByIds(int[] ids);
}
