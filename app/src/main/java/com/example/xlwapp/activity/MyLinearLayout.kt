package com.example.xlwapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.xlwapp.R
import com.example.xlwapp.databinding.MyLinearLayoutBinding
//简单实现 LinearLayout  、  bindingView 和 LiveData
class MyLinearLayout : AppCompatActivity() {

    lateinit var binding : MyLinearLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.my_linear_layout)
        binding.setButton.setOnClickListener(View.OnClickListener {
            binding.text1.text = binding.edit1.text
            binding.text1.visibility = View.VISIBLE
            binding.edit1.visibility = View.GONE
            binding.setButton.visibility = View.GONE
            binding.apply {
                binding.myname?.name = binding.text1.text.toString()
                invalidateAll()
            }
        })
        binding.myname = My("qwe")
    }

    data class My(var name : String)
}

