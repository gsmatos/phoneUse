package com.gsm.phoneuse.persistence


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScreenTimeDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(screenTime: ScreenTime)

    @Query("SELECT total_time from screen_time_table where time_key_col = :key")
    fun getLiveTime(key: String = timeKey): LiveData<Long>

    @Query("SELECT total_time from screen_time_table where time_key_col = :key")
    fun getTime(key: String = timeKey): Long

    @Query("delete from screen_time_table")
    fun clear()
}