package com.example.xlwapp.fragment.examplefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater,R.layout.fragment_game,container,false)
        binding.btnOver.setOnClickListener{v -> v.findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameOverFragment(
                binding.btnOver.text.toString()
            )
        ) }
        binding.btnWon.setOnClickListener{v -> v.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment()) }

        return binding.root
    }


}
