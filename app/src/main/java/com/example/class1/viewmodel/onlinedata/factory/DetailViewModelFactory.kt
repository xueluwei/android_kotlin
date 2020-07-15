
package com.example.xlwapp.viewmodel.onlinedata.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.xlwapp.network.MarsProperty
import com.example.xlwapp.viewmodel.onlinedata.DetailViewModel

class DetailViewModelFactory(
        private val marsProperty: MarsProperty,
        private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(marsProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
