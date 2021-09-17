package com.example.xlwapp.viewmodel.firebaseUI

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingViewModelFactory(
        private val context: Context
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingViewModel::class.java))
            return SettingViewModel(
                    context
            ) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}