package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    //반드시 private
    private var _score = 0
    val score:Int
        get() =_score
    private var _currentWordCount = 0
    val currentWordCount
        get() = _currentWordCount
    private lateinit var _currentScrambledWord: String
    //읽기만
    val currentScrambleWord : String
        get() = _currentScrambledWord

    private var wordList:MutableList<String> = mutableListOf()
    private lateinit var currentWord:String

    //초기화를 시켜야 첫 단어가 test로 나오지 않는다.
    init{
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    private fun getNextWord(){
        currentWord = allWordsList.random()
        //글자를 쪼개서 배열로
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        //currentWord와 tempWord가 같으면 셔플 시키기
        while(String(tempWord).equals(currentWord, false)){
            tempWord.shuffle()
        }
        if (wordList.contains(currentWord)){
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordList.add(currentWord)
        }
    }

    fun nextWord():Boolean {
        return if(currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else {
            false
        }
    }

    private fun increaseScore(){
        _score += SCORE_INCREASE
    }
    fun isUserWordCorrect(playerWord:String):Boolean {
        if(playerWord.equals(currentWord,true)){
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializeData(){
        _score = 0
        _currentWordCount = 0
        wordList.clear()
        getNextWord()
    }


}