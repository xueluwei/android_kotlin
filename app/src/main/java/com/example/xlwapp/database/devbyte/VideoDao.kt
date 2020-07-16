package com.example.xlwapp.database.devbyte

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {
    @Query("select * from databasevideo")
    fun getVideo(): LiveData<List<DatabaseVideo>>

    //onConflict replace all data already present
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(video: List<DatabaseVideo>)


}