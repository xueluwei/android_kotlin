package com.example.class1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
//ViewModel（用于处理saveinstance等数据保存）
// ViewModelFactory（用于初始化ViewModel）
// LiveData（用于ViewModel提供get访问，最好不变）
// MutableLiveData（用于ViewModel中对LiveData的操作，可变）
//DataBinding（可使Xml文件与ViewModel直接交互，在Xml中声明data使用lambda表达式）
class GuessGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_game)
    }
}


