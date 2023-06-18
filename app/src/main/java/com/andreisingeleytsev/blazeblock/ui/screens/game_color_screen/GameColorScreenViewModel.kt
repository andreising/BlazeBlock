package com.andreisingeleytsev.blazeblock.ui.screens.game_color_screen

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.blazeblock.ui.theme.SecondaryColor2
import com.andreisingeleytsev.blazeblock.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class GameColorScreenViewModel: ViewModel() {


    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: GameColorScreenEvent){
        when(event) {
            is GameColorScreenEvent.OnBack -> {
                sendUIEvent(UIEvents.BackStack)
            }
            is GameColorScreenEvent.OnSquarePressed -> {
                val squareIndex = event.count
                Log.d("tag", event.count.toString())
                checkIsGoing(squareIndex)
            }
        }
    }

    private fun checkIsGoing(count: Int) {
        if (isConnecting) checkSquare(count)
        else checkStartSquare(count)
    }

    private fun checkStartSquare(count: Int) {
        Log.d("tag", "${usedSquare.contains(count)} ${isConnecting}  ${checkAnotherColor(count)}")
        if (usedSquare.contains(count)) return
        if (checkAnotherColor(count)) startNewWay(count)
    }

    private fun startNewWay(count: Int) {
        var ampl = 1
        var thisCount = 1
        currentList.forEach {
            if (count==it.first||count==it.second) {
                finishSquare = if (count==it.first) it.second
                else it.first
                ampl=0
            }

            thisCount+=ampl
        }
        Log.d("tag", "$count  $finishSquare  ")
        currentColor = when(thisCount){
            1-> Color.Red
            2-> Color.Yellow
            3-> Color.Green
            4-> Color.Black
            else -> {
                SecondaryColor2
            }
        }

        usedSquare.add(count)
        isConnecting=true

    }

    private fun isNear(int: Int): Boolean {
        if (int==usedSquare.last()) return true
        if (int-5==usedSquare.last()) return true
        if (int+1==usedSquare.last()) return true
        if (int-1==usedSquare.last()) return true
        if (int+5==usedSquare.last()) return true
        return false
    }

    private fun isLastUsed(int: Int): Boolean {
        Log.d("taggg", "  $usedSquare")
        if (usedSquare.isEmpty()) return false
        return int==usedSquare.last()
    }

    private fun checkAnotherColor(int: Int): Boolean {
        currentList.forEach {
            if (int==it.first||int==it.second) return true
        }
        return false
    }

    private fun checkSquare(int: Int) {
        if (!isNear(int)) return
        Log.d("tag", "check")
        if (int==finishSquare) {
            usedSquare.add(int)
            endConnecting()
            return
        }

        if (isLastUsed(int)) {
            colorList[int].value = SecondaryColor2
            usedSquare.removeLast()
            isConnecting = false
            return
        }

        if (checkAnotherColor(int)) return
        usedSquare.add(int)
        colorList[int].value=currentColor
    }

    private fun endConnecting() {
        finishSquare = -1
        isConnecting = false
        currentColor = Color.Transparent
        score.value+=2
        if (usedSquare.size==25) endGame()
    }
    var context: Context? = null
    private fun endGame() {
        val sharedPreferences = context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val myData = sharedPreferences?.getInt("key", 0)
        sharedPreferences?.edit()?.putInt("key", myData!!+score.value)?.apply()
        sendUIEvent(UIEvents.OnNavigate(Routes.RESULT_SCREEN))
    }

    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    object Lists{
        val list1 = listOf(Pair(0,22), Pair(13,24), Pair(4,23), Pair(-1,-1))
        val list2 = listOf(Pair(0,12), Pair(2,21), Pair(3,22), Pair(-1,-1))
        val list3 = listOf(Pair(0,1), Pair(3,17), Pair(4,24), Pair(16,22))
        val list4 = listOf(Pair(0,4), Pair(1,3), Pair(7,20), Pair(8,23))
        val list5 = listOf(Pair(3,5), Pair(0,2), Pair(4,10), Pair(15,24))
    }

    val colorList =  listOf(
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
        mutableStateOf(SecondaryColor2),
    )
    var difficulty = 0
    var currentList = emptyList<Pair<Int, Int>>()
    fun setStartList(){
        currentList = when(difficulty){
            1->Lists.list1
            2->Lists.list2
            3->Lists.list3
            4->Lists.list4
            5->Lists.list5
            else -> Lists.list5
        }
    }
    fun setStartColors(){
        var count = 1
        currentList.forEach {
            when(count){
                1 -> paintSquare(it, Color.Red)
                2 -> paintSquare(it, Color.Yellow)
                3 -> paintSquare(it, Color.Green)
                4 -> paintSquare(it, Color.Black)
            }
            count++
        }
    }

    private fun paintSquare(pair: Pair<Int, Int>, color: Color) {
        if (pair.first<0) return
        colorList[pair.first].value = color
        colorList[pair.second].value = color
    }

    val score = mutableStateOf(0)
    private var isConnecting = false
    private val usedSquare = mutableListOf<Int>()
    private var currentColor = SecondaryColor2
    private var finishSquare = -1
}


