package com.example.xlwapp.viewmodel.gdg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GdgViewModel: ViewModel(){
    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    fun onFabClicked(){
        _navigateToSearch.value = true
    }

    fun onNavigatedToSearch(){
        _navigateToSearch.value = false
    }
}