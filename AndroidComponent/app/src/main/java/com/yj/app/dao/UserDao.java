package com.yj.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.yj.app.domain.User;
import com.yj.app.domain.UserAndPlayList;
import com.yj.app.domain.UserLibrary;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from user")
    List<User> getAll();

    @Query("select * from user where uid in (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("select * from user where first_name like :first and last_name like :last limit 1")
    User findByName(String first, String last);

    @Insert
    List<Long> insertAll(User... users);

    @Delete
    void delete(User user);

    @Transaction
    @Query("select * from user")
    List<UserLibrary> getUserLibrary();

    @Transaction
    @Query("SELECT * FROM user")
     List<UserAndPlayList> getUsersWithPlaylists();
}
