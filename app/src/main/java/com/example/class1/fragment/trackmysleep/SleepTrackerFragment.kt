package com.example.xlwapp.fragment.trackmysleep

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xlwapp.R
import com.example.xlwapp.database.trackmysleep.SleepDatabase
import com.example.xlwapp.databinding.FragmentSleepTrackerBinding
import com.example.xlwapp.recyclerview.sleeptrack.SleepNightAdapter
import com.example.xlwapp.recyclerview.sleeptrack.SleepNightGridAdapter
import com.example.xlwapp.recyclerview.sleeptrack.SleepNightListener
import com.example.xlwapp.viewmodel.sleeptrack.SleepTrackerViewModel
import com.example.xlwapp.viewmodel.sleeptrack.factory.SleepTrackerViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class SleepTrackerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

        val application = this.activity!!.application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val viewModel = ViewModelProviders.of(this,viewModelFactory).get(SleepTrackerViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.sleepTrackerViewModel = viewModel

        val adapter = SleepNightAdapter()
        binding.sleepList.adapter = adapter
        //用于处理DiffUtil添加item不移动到添加位置的问题
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.sleepList.scrollToPosition(positionStart)
            }
        });

        val manager = GridLayoutManager(activity, 3)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return when(position){
                    0 -> 3
                    else -> 1
                }
            }

        }
        binding.sleepGrid.layoutManager = manager
        val adapterG = SleepNightGridAdapter(SleepNightListener {
            viewModel.onSleepNightClicked(it)
        })
        binding.sleepGrid.adapter = adapterG
        adapterG.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.sleepGrid.scrollToPosition(positionStart)
            }
        });
        viewModel.navigateToSleepDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepDetailFragment(it))
                viewModel.onSleepNightNavigated()
            }
        })


        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentDirections
                            .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                viewModel.doNavigate()
        }})

        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT
                ).show()
                viewModel.doShowSnackBar()
            }
        })

        viewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmit(it)
                adapterG.addHeaderAndSubmit(it)
            }
        })


        return binding.root
    }
}
