package com.example.xlwapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.xlwapp.R

class CustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, MiniPaintActivity::class.java))
        }
        findViewById<Button>(R.id.button1).setOnClickListener {
            startActivity(Intent(this, ClippingExampleActivity::class.java))
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, SpotLightActivity::class.java))
        }
    }
}
