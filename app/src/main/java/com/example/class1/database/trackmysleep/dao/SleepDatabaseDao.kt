package com.example.xlwapp.database.trackmysleep.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.xlwapp.database.trackmysleep.entity.SleepNight

@Dao
interface SleepDatabaseDao{
    @Insert
    fun insert(sleepNight:SleepNight)

    @Update
    fun update(sleepNight:SleepNight)

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :key")
    fun get(key:Long):SleepNight?

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight():SleepNight?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNight():LiveData<List<SleepNight>>

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :key")
    fun getNigehtWithKey(key: Long): LiveData<SleepNight>
}
