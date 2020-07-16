package com.example.xlwapp.repository.devbyte

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.xlwapp.database.devbyte.DevByteDataBase
import com.example.xlwapp.database.devbyte.asDomainModel
import com.example.xlwapp.network.devbyte.DevByteNetwork
import com.example.xlwapp.network.devbyte.DevByteVideo
import com.example.xlwapp.network.devbyte.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(private val dataBase: DevByteDataBase) {

    val video: LiveData<List<DevByteVideo>> = Transformations.map(dataBase.videoDao.getVideo()){
        it.asDomainModel()
    }

    /**
     * Refresh the videos stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshVideo(){
        withContext(Dispatchers.IO){
            val playlist = DevByteNetwork.devbytes.getPlaylist().await()
            dataBase.videoDao.insertAll(playlist.asDomainModel())
        }
    }
}