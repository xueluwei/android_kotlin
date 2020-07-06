package com.example.class1.database.trackmysleep

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.class1.database.trackmysleep.dao.SleepDatabaseDao
import com.example.class1.database.trackmysleep.entity.SleepNight

@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase :RoomDatabase(){//抽象类原因为room提供实现方法
    abstract val sleepDatabaseDao:SleepDatabaseDao//一个db可以有多个dao

    //静态使可不用初始化而访问
    companion object{

        @Volatile
        private var INSTANCE:SleepDatabase? = null

        fun getInstance(context: Context):SleepDatabase{
            //synchronized预防多线程同时访问创建多个实例的问题
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance;
            }
        }
    }
}