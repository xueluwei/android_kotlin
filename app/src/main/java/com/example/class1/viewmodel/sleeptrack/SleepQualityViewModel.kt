package com.example.xlwapp.viewmodel.sleeptrack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xlwapp.database.trackmysleep.dao.SleepDatabaseDao
import com.example.xlwapp.database.trackmysleep.entity.SleepNight
import kotlinx.coroutines.*

class SleepQualityViewModel(
        private var sleepNightKey:Long = 0,
        val database : SleepDatabaseDao
):ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker:LiveData<Boolean?>
        get() = _navigateToSleepTracker

    init {

    }

    fun onSetQuality(quality: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val tonight = database.get(sleepNightKey) ?: return@withContext
                tonight.sleepQuality = quality
                database.update(tonight)
            }
            _navigateToSleepTracker.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doNavigate(){
        _navigateToSleepTracker.value = null
    }
}