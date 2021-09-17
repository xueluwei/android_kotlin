package com.example.xlwapp.viewmodel.firebaseUI

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xlwapp.R
import kotlin.random.Random

class SettingViewModel(
        private val context: Context
) : ViewModel() {

    var texts = context.resources.getStringArray(R.array.login_array).random()
    private val _goLogin = MutableLiveData<Boolean>()
    val goLogin: LiveData<Boolean>
        get() = _goLogin

    fun onClickButton(){
        _goLogin.value = true
    }

    fun onGoLogin(){
        _goLogin.value = false
    }
}