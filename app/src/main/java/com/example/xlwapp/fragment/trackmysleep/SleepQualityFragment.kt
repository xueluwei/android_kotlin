package com.example.xlwapp.fragment.trackmysleep

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.xlwapp.R
import com.example.xlwapp.database.trackmysleep.SleepDatabase
import com.example.xlwapp.databinding.FragmentSleepQualityBinding
import com.example.xlwapp.viewmodel.sleeptrack.SleepQualityViewModel
import com.example.xlwapp.viewmodel.sleeptrack.factory.SleepQualityViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class SleepQualityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val arguments = SleepQualityFragmentArgs.fromBundle(arguments!!)
        val application = this.activity!!.application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val binding = DataBindingUtil.inflate<FragmentSleepQualityBinding>(inflater,R.layout.fragment_sleep_quality,container,false)
        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(SleepQualityViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if(it == true){
                this.findNavController().navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                viewModel.doNavigate()
            }
        })
        return binding.root
    }

}
