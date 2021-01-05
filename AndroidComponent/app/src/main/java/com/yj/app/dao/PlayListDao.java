package com.yj.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.yj.app.domain.Library;
import com.yj.app.domain.Playlist;

import java.util.List;

@Dao
public interface PlayListDao {
    @Query("select * from play_list")
    List<Playlist> getAll();

    @Query("select * from play_list where id in (:ids)")
    List<Playlist> loadAllByIds(int[] ids);

    @Query("select * from play_list where play_name like :name")
    List<Playlist> findByName(String name);

    @Insert
    void insertAll(Playlist... students);

    @Delete
    void delete(Playlist student);
}
