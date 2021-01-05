package com.yj.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.yj.app.domain.Library;
import com.yj.app.domain.Student;

import java.util.List;

@Dao
public interface LibraryDao {
    @Query("select * from library")
    List<Library> getAll();

    @Query("select * from library where id in (:ids)")
    List<Library> loadAllByIds(int[] ids);

    @Query("select * from library where name like :name")
    List<Library> findByName(String name);

    @Insert
    void insertAll(Library... students);

    @Delete
    void delete(Library student);
}
