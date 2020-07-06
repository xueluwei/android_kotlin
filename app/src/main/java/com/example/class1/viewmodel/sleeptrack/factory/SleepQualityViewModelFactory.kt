package com.example.class1.viewmodel.sleeptrack.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.class1.database.trackmysleep.dao.SleepDatabaseDao
import com.example.class1.viewmodel.sleeptrack.SleepQualityViewModel

class SleepQualityViewModelFactory(
    private val sleepNightKey:Long,
    private val dataSource: SleepDatabaseDao
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)){
            return SleepQualityViewModel(sleepNightKey, dataSource) as T
        }
        throw IllegalAccessException("Unknown ViewModel Class")
    }

}