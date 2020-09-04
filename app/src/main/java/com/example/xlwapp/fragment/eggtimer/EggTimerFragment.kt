package com.example.xlwapp.fragment.eggtimer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentEggTimerBinding
import com.example.xlwapp.viewmodel.eggtimer.EggTimerViewModel

/**
 * A simple [Fragment] subclass.
 */
class EggTimerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentEggTimerBinding>(inflater, R.layout.fragment_egg_timer, container, false)
        val viewModel = ViewModelProvider(this).get(EggTimerViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        createChannel(getString(R.string.egg_notification_channel_id), getString(R.string.egg_notification_channel_name))
        return binding.root
    }

    private fun createChannel(channelId: String, channelName: String) {
        //do just above android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.breakfast_notification_channel_description)
            notificationChannel.setShowBadge(false)

            val notificationManager = requireActivity().getSystemService(
                    NotificationManager::class.java
            )
            notificationManager!!.createNotificationChannel(notificationChannel)
        }
    }

}
