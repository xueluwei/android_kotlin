package com.example.xlwapp.fragment.getonlinedata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentDetailBinding
import com.example.xlwapp.viewmodel.onlinedata.DetailViewModel
import com.example.xlwapp.viewmodel.onlinedata.factory.DetailViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val marsProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        val viewModelFactory = DetailViewModelFactory(marsProperty, this.activity!!.application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(inflater,R.layout.fragment_detail,container,false)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        return binding.root
    }

}
