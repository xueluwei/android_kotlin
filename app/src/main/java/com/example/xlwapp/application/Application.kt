package com.example.xlwapp.application

import android.app.Application
import android.os.Build
import androidx.multidex.MultiDexApplication
import androidx.work.*
import com.example.xlwapp.work.devbyte.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class Application : MultiDexApplication(){
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    //use to avoid block UI thread
    private fun delayedInit() {
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
            setupRecurringWork()
        }
    }

    fun setupRecurringWork(){
        val constraints = Constraints.Builder() // use to make constraints of work request
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        setRequiresDeviceIdle(true)
                    }
                }
                .build()

        //The OneTimeWorkRequest class is for one-off tasks. (A one-off task happens only once.)
        //The PeriodicWorkRequest class is for periodic work, work that repeats at intervals.

        //The minimum interval for periodic work is 15 minutes.
        // Periodic work can't have an initial delay as one of its constraints.
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
                //(15, TimeUnit.MINUTES) request once per 15 minutes
                .setConstraints(constraints) // set constraints of work request
                .build()


        //manage the repeat request
        WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, // keep the exists work request, discard the new one
                repeatingRequest
        )

        // can find result in logcat:  I/WM-WorkerWrapper
    }
}