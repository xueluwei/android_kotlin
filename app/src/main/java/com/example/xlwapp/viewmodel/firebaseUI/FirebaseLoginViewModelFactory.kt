package com.example.xlwapp.viewmodel.firebaseUI

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FirebaseLoginViewModelFactory(
        private val activity: Activity,
        private val viewLifecycleOwner: LifecycleOwner
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FirebaseLoginViewModel::class.java))
            return FirebaseLoginViewModel(
                    activity,
                    viewLifecycleOwner
            ) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}