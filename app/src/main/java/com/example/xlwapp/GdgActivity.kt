package com.example.xlwapp

import android.graphics.Bitmap
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.wifi.aware.AttachCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.xlwapp.databinding.ActivityGdgBinding
import com.example.xlwapp.network.gdg.NewGdgApi
import com.google.android.material.internal.NavigationMenuItemView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_gdg.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//设计相关
class GdgActivity : AppCompatActivity() {

    lateinit var binding: ActivityGdgBinding

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gdg)

        sutupNavigation()
    }

    private fun sutupNavigation() {
        // first find the nav controller
        val navController = findNavController(R.id.gdgFragment)
        // then setup the action bar, tell it about the DrawerLayout
        setupActionBarWithNavController(navController, binding.drawerLayout)
        // finally setup the left drawer (called a NavigationView)
        binding.navigationView.setupWithNavController(navController)
        //changes content after supportActionBar changed
        navController.addOnDestinationChangedListener{_, destination:NavDestination, _ ->
            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
            when(destination.id){
                R.id.gdgFragment -> {
                    toolBar.setDisplayUseLogoEnabled(true)
                    toolBar.setDisplayShowHomeEnabled(true)
                    toolBar.setLogo(R.drawable.ic_gdg)
                }
                else -> {
                    toolBar.setDisplayUseLogoEnabled(false)
                }
            }
        }

        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(binding.navigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(findNavController(R.id.gdgFragment), binding.drawerLayout)
    }


}
