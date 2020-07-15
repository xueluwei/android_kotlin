package com.example.xlwapp.viewmodel.onlinedata

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.xlwapp.R
import com.example.xlwapp.network.MarsProperty

class DetailViewModel(
        private val marsProperty: MarsProperty,
        private val application: Application
) : ViewModel() {
    private val _selectedProperty = MutableLiveData<MarsProperty>()
    val selecterProperty: LiveData<MarsProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = marsProperty
    }

    val displayPropertyPrice = Transformations.map(selecterProperty){
        application.applicationContext.getString(
                when(it.isRentail){
                    true -> R.string.display_price_monthly_rental
                    false -> R.string.display_price
                }, it.price
        )
    }

    val displayPropertyType = Transformations.map(selecterProperty){
        application.applicationContext.getString(
                when(it.isRentail){
                    true -> R.string.type_rent
                    false -> R.string.type_sale
                }
        )
    }




}