package com.example.xlwapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.xlwapp.R
import kotlinx.android.synthetic.main.dice.*
//简单的初始设置，button用法
class dickRoll : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dice)
        button.setOnClickListener(View.OnClickListener { image1.setImageResource(roll()) })
    }
    private fun roll() : Int {
        val i  =  (1..6).random()
        val res = when(i){
            1-> R.drawable.dice_1
            2-> R.drawable.dice_2
            3-> R.drawable.dice_3
            4-> R.drawable.dice_4
            5-> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        return res
    }
}