package com.example.xlwapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xlwapp.R
import com.example.xlwapp.utils.customviews.ClippedView
import com.example.xlwapp.utils.customviews.MyCanvasView

class ClippingExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ClippedView(this))
    }
}
