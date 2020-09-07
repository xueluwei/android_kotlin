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
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentEggTimerBinding
import com.example.xlwapp.viewmodel.eggtimer.EggTimerViewModel
import com.google.firebase.messaging.FirebaseMessaging

/**
 * A simple [Fragment] subclass.
 */
class EggTimerFragment : Fragment() {

    val TOPIC = "breakfast"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentEggTimerBinding>(inflater, R.layout.fragment_egg_timer, container, false)
        val viewModel = ViewModelProvider(this).get(EggTimerViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        createChannel(
                getString(R.string.egg_notification_channel_id),
                getString(R.string.egg_notification_channel_name)
        )
        createChannel(
                getString(R.string.breakfast_notification_channel_id),
                getString(R.string.breakfast_notification_channel_name)
        )

        subscribeTopic()
        return binding.root
    }

    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
                .addOnCompleteListener { task ->
                    var msg = getString(R.string.message_subscribed)
                    if (!task.isSuccessful) {
                        msg = getString(R.string.message_subscribe_failed)
                    }
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
    }

    private fun createChannel(channelId: String, channelName: String) {
        //do just above android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(false)
            }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.breakfast_notification_channel_description)

            val notificationManager = requireActivity().getSystemService(
                    NotificationManager::class.java
            )
            notificationManager!!.createNotificationChannel(notificationChannel)
        }
    }

}
