package com.example.xlwapp.fragment.examplefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentGameOverBinding

class GameOverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args =
            GameOverFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context,"name:${args.buttonName}",Toast.LENGTH_SHORT).show()

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameOverBinding>(inflater,R.layout.fragment_game_over,container,false)
        binding.doAgain.setOnClickListener{v -> v.findNavController().navigate(
            GameOverFragmentDirections.actionGameOverFragmentToGameFragment()) }
        return binding.root
    }

}
