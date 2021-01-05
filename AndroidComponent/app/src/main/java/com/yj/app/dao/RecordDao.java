package com.yj.app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.yj.app.domain.Record;
import com.yj.app.domain.User;

import java.util.Date;
import java.util.List;

@Dao
public interface RecordDao {

    @Query("select * from record")
    List<Record> queryAll();

    @Query("select * from record where id in (:ids)")
    List<Record> queryByIds(int[] ids);

    @Insert
    long save(Record record);

    @Insert
    List<Long> saveAll(User... users);

    @Query("select * from record where record_time >:start and record_time < :end")
    List<Record> queryByTime(Date start,Date end);


}
