package com.example.xlwapp.viewmodel.sleeptrack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.xlwapp.database.trackmysleep.dao.SleepDatabaseDao
import com.example.xlwapp.database.trackmysleep.entity.SleepNight
import com.example.xlwapp.utils.formatNights
import kotlinx.coroutines.*

class SleepTrackerViewModel(
        val database:SleepDatabaseDao,
        application: Application
) : AndroidViewModel(application){

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var nights = database.getAllNight()
    val nightString = Transformations.map(nights){ nights ->
        formatNights(nights, application.resources)
    }
    private var tonight = MutableLiveData<SleepNight?>()
    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateToSleepQuality:LiveData<SleepNight>
        get() = _navigateToSleepQuality

    val startButtonVisible = Transformations.map(tonight){
        it == null
    }
    val stopButtonVisible = Transformations.map(tonight){
        it != null
    }
    val clearButtonVisible = Transformations.map(nights){
        it?.isNotEmpty()
    }

    private val _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent : LiveData<Boolean>
        get() = _showSnackbarEvent

    private val _navigateToSleepDetail = MutableLiveData<Long>()
    val navigateToSleepDetail: LiveData<Long>
        get() = _navigateToSleepDetail

    init {
        initTonight()
    }

    private fun initTonight() {
        //开携程
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    //suspend挂起方法
    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO){
            var night = database.getTonight()
            if(night?.endTimeMilli != night?.startTimeMilli){
                night = null
            }
            night
        }
    }

    fun onStartTracking(){
        uiScope.launch{
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(night:SleepNight){
        withContext(Dispatchers.IO){
            database.insert(night)
        }
    }

    fun onStopTracking(){
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun update(night:SleepNight){
        withContext(Dispatchers.IO){
            database.update(night)
        }
    }

    fun onClear(){
        uiScope.launch {
            clear()
            tonight.value = null
            _showSnackbarEvent.value = true
        }
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doNavigate(){
        _navigateToSleepQuality.value = null
    }

    fun doShowSnackBar(){
        _showSnackbarEvent.value = false
    }

    fun onSleepNightClicked(id: Long){
        _navigateToSleepDetail.value = id
    }

    fun onSleepNightNavigated(){
        _navigateToSleepDetail.value = null
    }
}