package com.example.xlwapp.fragment.gdg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentGdgBinding
import com.example.xlwapp.viewmodel.gdg.GdgViewModel

/**
 * A simple [Fragment] subclass.
 */
class GdgFragment : Fragment() {

    lateinit var viewModel: GdgViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentGdgBinding>(inflater, R.layout.fragment_gdg,container, false)
        viewModel = ViewModelProvider(this).get(GdgViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.navigateToSearch.observe(viewLifecycleOwner, Observer {
            if (it){
                val navController = findNavController()
                navController.navigate(R.id.action_gdgFragment_to_gdgListFragment)
                viewModel.onNavigatedToSearch()
            }
        })
        return binding.root
    }

}
