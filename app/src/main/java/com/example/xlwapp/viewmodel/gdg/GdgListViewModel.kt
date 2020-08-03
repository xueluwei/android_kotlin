package com.example.xlwapp.viewmodel.gdg

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xlwapp.network.gdg.*
import com.example.xlwapp.repository.gdg.GdgChapterRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class GdgListViewModel: ViewModel(){

    private val repository = GdgChapterRepository(NewGdgApi.retrofitService)

    private var filter = FilterHolder()

    private var currentJob: Job? = null

    private val _gdgList = MutableLiveData<List<NewChapter>>()
    private val _regionList = MutableLiveData<List<String>>()
    private val _showNeedLocation = MutableLiveData<Boolean>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val gdgList: LiveData<List<NewChapter>>
        get() = _gdgList

    val regionList: LiveData<List<String>>
        get() = _regionList

    val showNeedLocation: LiveData<Boolean>
        get() = _showNeedLocation

    init {
        // process the initial filter
        onQueryChanged()

        viewModelScope.launch {
            delay(5_000)
            _showNeedLocation.value = !repository.isFullyInitialized
        }
    }

    private fun onQueryChanged() {
        currentJob?.cancel() // if a previous query is running cancel it before starting another
        currentJob = viewModelScope.launch {
            try {
                _gdgList.value = repository.getChaptersForFilter(filter.currentValue)
                repository.getFilters().let {
                    // only update the filters list if it's changed since the last time
                    if (it != _regionList.value) {
                        _regionList.value = it
                    }
                }
            } catch (e: IOException) {
                _gdgList.value = listOf()
            }
        }
    }

    fun onLocationUpdated(location: Location) {
        viewModelScope.launch {
            repository.onLocationChanged(location)
            onQueryChanged()
        }
    }

    fun onFilterChanged(filter: String, isChecked: Boolean) {
        if (this.filter.update(filter, isChecked)) {
            onQueryChanged()
        }
    }

    private class FilterHolder {
        var currentValue: String? = null
            private set

        fun update(changedFilter: String, isChecked: Boolean): Boolean {
            if (isChecked) {
                currentValue = changedFilter
                return true
            } else if (currentValue == changedFilter) {
                currentValue = null
                return true
            }
            return false
        }
    }
}