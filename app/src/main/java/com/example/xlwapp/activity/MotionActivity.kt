package com.example.xlwapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.xlwapp.R

class MotionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion)
        findViewById<Button>(R.id.button1).setOnClickListener{
            startActivity(Intent(this, Motion2Activity::class.java))
        }
        findViewById<Button>(R.id.button2).setOnClickListener{
            startActivity(Intent(this, Motion3Activity::class.java))
        }
        findViewById<Button>(R.id.button3).setOnClickListener{
            startActivity(Intent(this, Motion4Activity::class.java))
        }
    }
}