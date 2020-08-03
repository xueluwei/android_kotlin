package com.example.xlwapp.viewmodel.gdg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddGdgViewModel: ViewModel(){
    private var _showSnackbarEvent = MutableLiveData<Boolean?>()
    val showSnackbarEvent: LiveData<Boolean?>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar(){
        _showSnackbarEvent.value = null
    }

    fun onSubmitApplication(){
        _showSnackbarEvent.value = true
    }
}