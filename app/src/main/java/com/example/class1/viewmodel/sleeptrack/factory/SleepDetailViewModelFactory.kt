package com.example.class1.viewmodel.sleeptrack.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.class1.database.trackmysleep.dao.SleepDatabaseDao
import com.example.class1.viewmodel.sleeptrack.SleepDetailViewModel

class SleepDetailViewModelFactory(
    private val key: Long,
    private val dataSource: SleepDatabaseDao
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SleepDetailViewModel::class.java)){
            return SleepDetailViewModel(key, dataSource) as T
        }
        throw IllegalAccessException("Unknown ViewModel Class")
    }

}