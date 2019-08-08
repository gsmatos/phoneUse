package com.gsm.phoneuse.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


const val timeKey = "time_Key"

@Entity(tableName = "screen_time_table")
data class ScreenTime(
    @PrimaryKey
    @ColumnInfo(name = "time_key_col")
    val _timeKey: String = timeKey,

    @ColumnInfo(name = "total_time")
    var time: Long = 0L

)