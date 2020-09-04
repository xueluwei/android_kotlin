package com.example.xlwapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.amitshekhar.DebugDB
import com.example.xlwapp.BuildConfig
import com.example.xlwapp.R

//room数据存储
//携程
class TrackMySleepQualityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_my_sleep_quality)
        //拿到room地址直接在浏览器打开可直接操作
//            Log.e("testdb", DebugDB.getAddressLog())
    }

}