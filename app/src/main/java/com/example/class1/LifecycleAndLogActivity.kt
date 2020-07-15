package com.example.xlwapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

//timber打log可以不必用TAG直接输出message,需要在application初始化
//lifecycleObserve 感知 lifecycle 每个activity和fragment都有
//重写onsaveinstancestate方法放bundle在oncreate里面取
class LifecycleAndLogActivity : AppCompatActivity() {

//    private lateinit var dessertTimer : DessertTimer;
    private val TEST_SAVE_INSTANCE = "test_save_instance"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_and_log)

//        dessertTimer = DessertTimer(this.lifecycle)

        Timber.e("oncreate")
        if(savedInstanceState != null){
            Toast.makeText(this,savedInstanceState.getString(TEST_SAVE_INSTANCE),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEST_SAVE_INSTANCE,"okok")

        Timber.e("onsaveinstancestate")
    }

    override fun onStart() {
        super.onStart()
//        dessertTimer.startTimer()
        Timber.e("onstart")
    }

    override fun onResume() {
        super.onResume()
        Timber.e("onresume")
    }

    override fun onPause() {
        super.onPause()
        Timber.e("onpause")
    }

    override fun onStop() {
        super.onStop()
//        dessertTimer.stopTimer()
        Timber.e("onstop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("ondestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.e("onrestart")
    }
}
