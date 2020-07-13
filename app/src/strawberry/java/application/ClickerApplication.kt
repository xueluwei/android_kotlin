package com.example.class1.application

import android.app.Application
import androidx.multidex.MultiDexApplication
import timber.log.Timber

class ClickerApplication : MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}