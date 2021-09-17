package com.example.xlwapp.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.xlwapp.R


class HuaweiPushActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huawei_push)
        val text = findViewById<TextView>(R.id.tokenText)
        if(intent != null && intent.hasExtra("fromReceiver")) {
            if(intent.extras!!.getBoolean("fromReceiver")){
                text.text = intent.extras!!.getString("token")
            }
        }
        val receiver = MyReceiver()
        val filter = IntentFilter()
        filter.addAction("com.huawei.codelabpush.ON_NEW_TOKEN")
        this@HuaweiPushActivity.registerReceiver(receiver, filter)
    }

    class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if ("com.huawei.codelabpush.ON_NEW_TOKEN" == intent.action) {
                val token = intent.getStringExtra("token")
                val int = Intent(context, HuaweiPushActivity::class.java)
                int.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                int.putExtra("fromReceiver", true)
                int.putExtra("token", token)
                context!!.startActivity(int)
                Toast.makeText(context, token, Toast.LENGTH_LONG).show()
            }
        }
    }
}