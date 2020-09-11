package com.example.xlwapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import com.example.xlwapp.R
import com.example.xlwapp.utils.customviews.MyCanvasView

class MiniPaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myCanvansView = MyCanvasView(this)
        myCanvansView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        myCanvansView.contentDescription = getString(R.string.canvasContentDescription)
        setContentView(myCanvansView)
    }
}
