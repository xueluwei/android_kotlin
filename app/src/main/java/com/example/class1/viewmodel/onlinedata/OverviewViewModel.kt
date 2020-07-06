package com.example.class1.viewmodel.onlinedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.class1.network.MarsApi
import kotlinx.coroutines.*
import java.lang.Exception

class OverviewViewModel : ViewModel(){

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties() {
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
            val getPropertiesDeferred = MarsApi.retrofitService.getPropertiesAsync()
            try {
                val listResult = getPropertiesDeferred.await()
                _response.value = "Success: ${listResult.size} Mars properties retrieved"
            }catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }


}