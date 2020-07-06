package com.example.class1.viewmodel.sleeptrack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.class1.database.trackmysleep.dao.SleepDatabaseDao
import com.example.class1.database.trackmysleep.entity.SleepNight
import kotlinx.coroutines.*

class SleepDetailViewModel(
        private val key: Long = 0L,
        private val dataSource: SleepDatabaseDao
) : ViewModel(){
    private val database = dataSource

    private val night: LiveData<SleepNight>
    fun getNight() = night

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    init {
        night = database.getNigehtWithKey(key)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onClose(){
        _navigateToSleepTracker.value = true
    }

    fun doneNavigating(){
        _navigateToSleepTracker.value = null
    }

}