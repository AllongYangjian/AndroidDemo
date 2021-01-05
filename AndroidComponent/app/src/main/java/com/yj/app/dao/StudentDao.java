package com.yj.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.yj.app.domain.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("select * from student")
    List<Student> getAll();

    @Query("select * from student where id in (:ids)")
    List<Student> loadAllByIds(int[] ids);

    @Query("select * from student where name like :name")
    List<Student> findByName(String name);

    @Insert
    void insertAll(Student... students);

    @Delete
    void delete(Student student);
}
