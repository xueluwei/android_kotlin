package com.example.xlwapp.fragment.firebaseUI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.xlwapp.R
import com.example.xlwapp.databinding.SettingFragmentBinding
import com.example.xlwapp.viewmodel.firebaseUI.SettingViewModel
import com.example.xlwapp.viewmodel.firebaseUI.SettingViewModelFactory

class SettingFragment : Fragment() {

    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: SettingFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, SettingViewModelFactory(requireContext())).get(SettingViewModel::class.java)
        viewModel.goLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToFirebaseLoginFragment(viewModel.texts))
                viewModel.onGoLogin()
            }
        })
        binding.viewModel = viewModel
    }

}