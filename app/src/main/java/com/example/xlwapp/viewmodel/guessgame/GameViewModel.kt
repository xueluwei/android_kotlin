package com.example.xlwapp.viewmodel.guessgame

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel : ViewModel(){

    companion object{
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 10000L
    }

    private val _currentTime = MutableLiveData<Long>()
    val currentTime:LiveData<Long>
        get() = _currentTime
    val currentTimeString = Transformations.map(currentTime){ time ->
        DateUtils.formatElapsedTime(time)
    }
    private lateinit var timer : CountDownTimer
    private val _score = MutableLiveData<Int>()
    val score:LiveData<Int>
        get() = _score
    lateinit var words:MutableList<String>
    val _currentWord= MutableLiveData<String>()
    val currentWord:LiveData<String>
        get() = _currentWord
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish:LiveData<Boolean>
        get() = _eventGameFinish

    init {
        initTimer()
        initList()
        Timber.e("init")
    }

    private fun initTimer() {
        timer = object : CountDownTimer(
            COUNTDOWN_TIME,
            ONE_SECOND
        ){

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ ONE_SECOND

            }

            override fun onFinish() {
                _currentTime.value =
                    DONE
                onGameFinish()
            }
        }
        timer.start()

    }

    override fun onCleared() {
        Timber.e("oncleared")
        super.onCleared()
        timer.cancel()
    }

    private fun initList() {
        _score.value = 0
        _currentWord.value = ""
        Timber.e("initList")
        words = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        words.shuffle()
        _currentWord.value = words.removeAt(0)
    }


    fun skipGuess() {
        toNext(-1)
    }

    fun gotTheGuess() {
        toNext(1)
    }


    private fun toNext(i:Int){
        _score.value = i + _score.value!!;
//        when (i){
//            1-> score.value = score.value?.plus(1)
//            -1 -> score.value = score.value?.minus(1)
//        }
        if(!words.isEmpty()) {
            _currentWord.value = words.removeAt(0)
        }else{
            onGameFinish()
        }
    }

    fun onGameFinish(){
        _eventGameFinish.value = true
    }

}