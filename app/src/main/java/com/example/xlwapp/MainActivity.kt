package com.example.xlwapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

//主页
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDarkMode()
        button1.setOnClickListener(View.OnClickListener { this.startActivity(Intent(this, dickRoll::class.java))})
        button2.setOnClickListener(View.OnClickListener { this.startActivity(Intent(this, MyLinearLayout::class.java))})
        button3.setOnClickListener(View.OnClickListener { this.startActivity(Intent(this, MyFragmentActivity::class.java)) })
        button4.setOnClickListener(View.OnClickListener { this.startActivity(Intent(this, LifecycleAndLogActivity::class.java)) })
        button5.setOnClickListener(View.OnClickListener { this.startActivity(Intent(this, GuessGameActivity::class.java)) })
        button6.setOnClickListener(View.OnClickListener { this.startActivity(Intent(this, TrackMySleepQualityActivity::class.java)) })
        button7.setOnClickListener(View.OnClickListener { this.startActivity(Intent(this, GetOnlineDataActivity::class.java)) })
        button8.setOnClickListener(View.OnClickListener { this.startActivity(Intent(this, DevByteActivity::class.java)) })
        button9.setOnClickListener(View.OnClickListener { this.startActivity(Intent(this, GdgActivity::class.java)) })

    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }


}


