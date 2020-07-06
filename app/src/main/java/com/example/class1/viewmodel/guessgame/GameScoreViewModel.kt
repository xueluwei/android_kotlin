package com.example.class1.viewmodel.guessgame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameScoreViewModel(finalScore: Int) : ViewModel() {
    val _score = MutableLiveData<Int>()
    val score:LiveData<Int>
        get() = _score

    val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain:LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        _score.value = finalScore
        Timber.e("initScore $finalScore")
    }

    fun onPlayAgain(){
        _eventPlayAgain.value = true;
    }

    fun onPlayAgainFinish(){
        _eventPlayAgain.value = false;
    }
}