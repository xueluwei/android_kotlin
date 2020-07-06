package com.example.class1.viewmodel.guessgame.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.class1.viewmodel.guessgame.GameScoreViewModel

class GameScoreViewModelFactory(private val finalScore: Int):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameScoreViewModel::class.java))
            return GameScoreViewModel(
                finalScore
            ) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}