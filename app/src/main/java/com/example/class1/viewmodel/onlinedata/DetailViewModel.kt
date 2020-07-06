package com.example.class1.viewmodel.onlinedata

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.class1.network.MarsProperty

class DetailViewModel(
        private val marsProperty: MarsProperty,
        private val application: Application
) : ViewModel(){

}