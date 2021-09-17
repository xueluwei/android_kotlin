package com.example.xlwapp.fragment.firebaseUI

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.xlwapp.viewmodel.firebaseUI.FirebaseLoginViewModel
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FirebaseLoginFragmentBinding
import com.example.xlwapp.viewmodel.firebaseUI.FirebaseLoginViewModelFactory
import com.example.xlwapp.viewmodel.firebaseUI.SIGN_IN_RESULT_CODE
import com.firebase.ui.auth.IdpResponse

class FirebaseLoginFragment : Fragment() {

    private lateinit var viewModel: FirebaseLoginViewModel
    private lateinit var binding: FirebaseLoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.firebase_login_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, FirebaseLoginViewModelFactory(requireActivity(), viewLifecycleOwner))
                .get(FirebaseLoginViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.goSetting.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(FirebaseLoginFragmentDirections.actionFirebaseLoginFragmentToSettingFragment())
                viewModel.onGoSetting()
            }
        })
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        if(arguments != null){
            val args = FirebaseLoginFragmentArgs.fromBundle(arguments)
            viewModel.setText(args.changedText)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in user.
                Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()
            } else {
                // Sign in failed. If response is null the user canceled the sign-in flow using
                // the back button. Otherwise check response.getError().getErrorCode() and handle
                // the error.
                Toast.makeText(activity, "error " + response?.error?.errorCode, Toast.LENGTH_SHORT).show()
            }
        }
    }
}