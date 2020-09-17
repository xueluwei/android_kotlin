package com.example.xlwapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.xlwapp.R
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_motion4.*

class Motion4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion4)
        supportActionBar!!.hide()
        coordinateMotion()
    }

    private fun coordinateMotion() {
        val appBarLayout: AppBarLayout = findViewById(R.id.appbar_layout)
        val motionLayout: MotionLayout = findViewById(R.id.motion_layout)

        val listener = AppBarLayout.OnOffsetChangedListener { unused, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            motionLayout.progress = seekPosition
        }

        appBarLayout.addOnOffsetChangedListener(listener)
    }
}