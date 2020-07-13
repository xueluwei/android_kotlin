package com.example.class1.viewmodel.onlinedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.class1.network.MarsApi
import com.example.class1.network.MarsApiFilter
import com.example.class1.network.MarsProperty
import kotlinx.coroutines.*
import java.lang.Exception

enum class MarsApiStatus {LOADING, ERROR, DONE}

class OverviewViewModel : ViewModel(){

    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the response String
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _property = MutableLiveData<List<MarsProperty>>()
    val property: LiveData<List<MarsProperty>>
        get() = _property

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
////        MarsApi.retrofitService.getProperties().enqueue(
////                //标准化
//////                object : retrofit2.Callback<String> {
//////                    override fun onFailure(call: Call<String>, t: Throwable) {
//////                        _response.value = "Failure" + t.message
//////                    }
//////
//////                    override fun onResponse(call: Call<String>, response: Response<String>) {
//////                        _response.value = response.body()
//////                    }
//////
//////                }
////                //moshi
////                object : Callback<List<MarsProperty>>{
////                    override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
////                        _response.value = "Failure" + t.message
////                    }
////
////                    override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
////                        _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
////                    }
////
////                }
////        )
//
        coroutineScope.launch {
            val getPropertiesDeferred = MarsApi.retrofitService.getPropertiesAsync(filter.value)
            try {
                _status.value = MarsApiStatus.LOADING
                val listResult = getPropertiesDeferred.await()
                _status.value = MarsApiStatus.DONE
                _property.value = listResult
            }catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _property.value = ArrayList()
            }
        }
    }

    fun updateFilter(filter: MarsApiFilter){
        getMarsRealEstateProperties(filter)
    }

    fun displayPropertyDetails(marsProperty: MarsProperty){
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete(){
        _navigateToSelectedProperty.value = null
    }
}