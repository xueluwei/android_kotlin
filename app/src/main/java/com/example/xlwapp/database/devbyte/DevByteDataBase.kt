package com.example.xlwapp.database.devbyte

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseVideo::class], version = 1)
abstract class DevByteDataBase: RoomDatabase(){
    abstract val videoDao: VideoDao

    companion object{
        private lateinit var INSTANCE: DevByteDataBase

        fun getDataBase(context: Context): DevByteDataBase{
            synchronized(DevByteDataBase::class.java){
                // The .isInitialized Kotlin property returns true if the lateinit property
                // (INSTANCE in this example) has been assigned a value, and false otherwise.
                if(!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DevByteDataBase::class.java,
                            "videos"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
