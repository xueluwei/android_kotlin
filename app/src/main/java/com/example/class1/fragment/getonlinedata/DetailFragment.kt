package com.example.class1.fragment.getonlinedata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.class1.R
import com.example.class1.databinding.FragmentDetailBinding
import com.example.class1.viewmodel.onlinedata.DetailViewModel
import com.example.class1.viewmodel.onlinedata.factory.DetailViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val marsProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        val viewModelFactory = DetailViewModelFactory(marsProperty, this.activity!!.application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(inflater,R.layout.fragment_detail,container,false)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        return binding.root
    }

}
