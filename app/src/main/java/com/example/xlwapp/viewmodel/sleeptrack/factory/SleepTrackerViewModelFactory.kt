package com.example.xlwapp.viewmodel.sleeptrack.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.xlwapp.database.trackmysleep.dao.SleepDatabaseDao
import com.example.xlwapp.viewmodel.sleeptrack.SleepTrackerViewModel
import java.lang.IllegalArgumentException

class SleepTrackerViewModelFactory(
        private val dataSource:SleepDatabaseDao,
        private val application: Application
) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)){
            return SleepTrackerViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}