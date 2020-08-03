package com.example.xlwapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.xlwapp.databinding.MyFragmentActivityBinding

//实现了 Fragment 各种跳转，Menu跳转
// drawerLayout 拖拽的 menu (item里id如果和navigation里的fragment的id相同的话不用写onclick，点击自动跳转)
// Navigation导航
//res中的navigation直接可视化导航
class MyFragmentActivity : AppCompatActivity(){

    //用于展现返回按钮等
    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<MyFragmentActivityBinding>(this,R.layout.my_fragment_activity)
        drawerLayout = binding.drawerLayout
        drawerLayout.setStatusBarBackground(R.color.colorAccent)
        val navigableSet = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navigableSet,drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navigableSet)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }
}