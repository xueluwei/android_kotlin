package com.example.xlwapp.fragment.guessgames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentGuessGameBinding
import com.example.xlwapp.databinding.FragmentGuessTitleBinding
import com.example.xlwapp.databinding.FragmentTitleBinding
import kotlinx.android.synthetic.main.fragment_guess_score.*

class GuessTitleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGuessTitleBinding>(inflater,R.layout.fragment_guess_title,container,false)
        binding.playGuessGame.setOnClickListener{v -> v.findNavController().navigate(GuessTitleFragmentDirections.actionGuessTitleFragmentToGuessGameFragment()) }
        return binding.root
    }
}