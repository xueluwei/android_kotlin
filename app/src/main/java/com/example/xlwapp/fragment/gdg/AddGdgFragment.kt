package com.example.xlwapp.fragment.gdg

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.NavigationUI
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentAddGdgBinding
import com.example.xlwapp.viewmodel.gdg.AddGdgViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class AddGdgFragment : Fragment() {

    private val viewModel: AddGdgViewModel by lazy {
        ViewModelProvider(this).get(AddGdgViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAddGdgBinding>(inflater,R.layout.fragment_add_gdg, container, false)
        binding.viewModel = viewModel
        //enable Observe LiveData
        binding.lifecycleOwner = this
        viewModel.showSnackbarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        getString(R.string.application_submitted),
                        Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                viewModel.doneShowingSnackbar()
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

}
