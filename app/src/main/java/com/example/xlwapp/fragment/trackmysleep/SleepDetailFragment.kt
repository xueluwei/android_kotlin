package com.example.xlwapp.fragment.trackmysleep

import android.os.Bundle
import android.util.Log
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
import com.example.xlwapp.databinding.FragmentSleepDetailBinding
import com.example.xlwapp.viewmodel.sleeptrack.SleepDetailViewModel
import com.example.xlwapp.viewmodel.sleeptrack.factory.SleepDetailViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class SleepDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentSleepDetailBinding>(inflater,R.layout.fragment_sleep_detail,container,false)

        val args = SleepDetailFragmentArgs.fromBundle(arguments!!)
        val dataSource = SleepDatabase.getInstance(activity!!.application).sleepDatabaseDao
        val viewModelFactory = SleepDetailViewModelFactory(args.sleepNightKey, dataSource)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(SleepDetailViewModel::class.java)
        binding.viewModel = viewModel

        binding.setLifecycleOwner(this)
        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if(it == true){
                this.findNavController().navigate(SleepDetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment())
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }

}
