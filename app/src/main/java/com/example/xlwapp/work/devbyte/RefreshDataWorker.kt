package com.example.xlwapp.work.devbyte

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.xlwapp.database.devbyte.DevByteDataBase
import com.example.xlwapp.repository.devbyte.VideosRepository
import retrofit2.HttpException

class RefreshDataWorker(
        appContext: Context,
        params: WorkerParameters
): CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "com.example.xlwapp.work.devbyte.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = DevByteDataBase.getDataBase(applicationContext)
        val repository = VideosRepository(database)

        try {
            repository.refreshVideo()
        }catch (e: HttpException){
            e.printStackTrace()
            return Result.retry()
        }
        return Result.success()
    }

}