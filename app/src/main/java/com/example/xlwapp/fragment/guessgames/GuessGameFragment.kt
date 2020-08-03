package com.example.xlwapp.fragment.guessgames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentGuessGameBinding
import com.example.xlwapp.viewmodel.guessgame.GameViewModel

/**
 * A simple [Fragment] subclass.
 */
class GuessGameFragment : Fragment() {


    private lateinit var binding:FragmentGuessGameBinding

    private lateinit var viewModle: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModle = ViewModelProvider(this).get(GameViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_guess_game,container,false)
        binding.guessGameViewModel = viewModle
        binding.lifecycleOwner = viewLifecycleOwner
        viewModle.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished){
                Toast.makeText(context,"Game Finished", Toast.LENGTH_SHORT).show()
                this.findNavController().navigate(GuessGameFragmentDirections.actionGuessGameFragmentToGuessScoreFragment(viewModle.score.value?:0))
            }
        })
        initView()
        return binding.root
    }

    private fun initView() {
        binding.guessText.text = viewModle.currentWord.value
        binding.guessScore.text = viewModle.score.value.toString()
    }

    private fun skipGuess() {
        viewModle.skipGuess()
        toNext()
    }

    private fun gotTheGuess() {
        viewModle.gotTheGuess()
        toNext()
    }

    private fun toNext(){
        if(!viewModle.words.isEmpty()){
            binding.guessText.text = viewModle.currentWord.value
            binding.guessScore.text = viewModle.score.value.toString()
        }else{
            viewModle.onGameFinish()
        }
    }
}
