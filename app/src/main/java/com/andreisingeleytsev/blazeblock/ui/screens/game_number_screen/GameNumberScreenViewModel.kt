package com.andreisingeleytsev.blazeblock.ui.screens.game_number_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.blazeblock.ui.theme.MainColor
import com.andreisingeleytsev.blazeblock.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class GameNumberScreenViewModel : ViewModel() {


    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: GameNumberScreenEvent){
        when(event) {
            is GameNumberScreenEvent.OnFieldBoxPressed -> {
                val squareIndex = event.count
                if (isSecondaryBoxPressed){
                    list[squareIndex].value = secondaryList[secondaryBox].value
                    isSecondaryBoxPressed = false
                    addBoxToList(squareIndex)
                    if (wayList.size>=3) {
                        score.value+=2
                        secondaryList[secondaryBox].value = Pair(0, Color.Black)
                        wayList.forEach{
                            var newIndex = 0
                            for (i in 1..5) {
                                newIndex = it - 5
                                if (!wayList.contains(newIndex)) break
                            }
                            if (newIndex<0) list[it].value = MainList.mainList.random()
                            else {
                                list[it].value = list[newIndex].value
                                upperSquare(newIndex)
                            }
                        }
                        wayList.clear()
                        var isOver = true
                        secondaryList.forEach {
                            if (it.value.first!=0) {
                                isOver = false
                                return@forEach
                            }
                        }
                        if (isOver) {
                            val sharedPreferences = context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                            val myData = sharedPreferences?.getInt("key", 0)
                            sharedPreferences?.edit()?.putInt("key", myData!!+score.value)?.apply()
                            sendUIEvent(UIEvents.OnNavigate(Routes.RESULT_SCREEN))
                        }
                    }

                }else {
                    if (isBoxChoosed()) {
                        if (currentBox == squareIndex) {
                            currentBox = -1
                        } else {

                            val changedBox = list[currentBox].value
                            val thisBox = list[squareIndex].value
                            list[currentBox].value = thisBox
                            list[squareIndex].value = changedBox
                            Log.d("tag", "${list[currentBox].value} ${list[squareIndex].value}")
                            currentBox = -1
                        }
                    } else {

                        currentBox=squareIndex
                    }
                }

            }
            is GameNumberScreenEvent.OnBack -> {
                sendUIEvent(UIEvents.BackStack)
            }
            is GameNumberScreenEvent.OnSecondaryBoxPressed -> {
                if (isSecondaryBoxPressed) {
                    secondaryBox = -1
                    isSecondaryBoxPressed = false
                }
                else {
                    isSecondaryBoxPressed = true
                    secondaryBox = event.index
                }
            }
        }
    }
    private fun addBoxToList(squareIndex: Int){
        if (!wayList.contains(squareIndex)) {
            wayList.add(squareIndex)
            findNearBoxes(squareIndex)
        }

    }
    private fun findNearBoxes(squareIndex: Int) {
        val text = list[squareIndex].value.first
        if (squareIndex in 1..3) {
            if (text==list[squareIndex-1].value.first) addBoxToList(squareIndex-1)
            if (text==list[squareIndex+1].value.first) addBoxToList(squareIndex+1)
            if (text==list[squareIndex+5].value.first) addBoxToList(squareIndex+5)
            return
        }
        if (squareIndex in 21..23) {
            if (text==list[squareIndex-1].value.first) addBoxToList(squareIndex-1)
            if (text==list[squareIndex+1].value.first) addBoxToList(squareIndex+1)
            if (text==list[squareIndex-5].value.first) addBoxToList(squareIndex-5)
            return
        }
        if (squareIndex == 5 ||squareIndex == 10 ||squareIndex == 15) {
            if (text==list[squareIndex-5].value.first) addBoxToList(squareIndex-5)
            if (text==list[squareIndex+1].value.first) addBoxToList(squareIndex+1)
            if (text==list[squareIndex+5].value.first) addBoxToList(squareIndex+5)
            return
        }
        if (squareIndex == 9 ||squareIndex == 14 ||squareIndex == 19) {
            if (text==list[squareIndex-1].value.first) addBoxToList(squareIndex-1)
            if (text==list[squareIndex-5].value.first) addBoxToList(squareIndex-5)
            if (text==list[squareIndex+5].value.first) addBoxToList(squareIndex+5)
            return
        }
        if (squareIndex == 0) {
            if (text==list[squareIndex+5].value.first) addBoxToList(squareIndex+5)
            if (text==list[squareIndex+1].value.first) addBoxToList(squareIndex+1)
            return
        }
        if (squareIndex == 4) {
            if (text==list[squareIndex-1].value.first) addBoxToList(squareIndex-1)
            if (text==list[squareIndex+5].value.first) addBoxToList(squareIndex+5)
            return
        }
        if (squareIndex == 20) {
            if (text==list[squareIndex+1].value.first) addBoxToList(squareIndex+1)
            if (text==list[squareIndex-5].value.first) addBoxToList(squareIndex-5)
            return
        }
        if (squareIndex == 24) {
            if (text==list[squareIndex-1].value.first) addBoxToList(squareIndex-1)
            if (text==list[squareIndex-5].value.first) addBoxToList(squareIndex-5)
        }
        else {
            if (text==list[squareIndex+1].value.first) addBoxToList(squareIndex+1)
            if (text==list[squareIndex-1].value.first) addBoxToList(squareIndex-1)
            if (text==list[squareIndex-5].value.first) addBoxToList(squareIndex-5)
            if (text==list[squareIndex+5].value.first) addBoxToList(squareIndex+5)
        }
    }
    private fun upperSquare(index: Int) {
        var newIndex = 0
        var pair = Pair(0, Color.Black)
        for (i in 1..5) {
            newIndex = index - 5
            if (wayList.contains(newIndex)) continue
            if (newIndex<0) {
                list[index].value = MainList.mainList.random()
                break
            }
            list[index].value = list[newIndex].value
            upperSquare(newIndex)
            break
        }
    }

    private var isSecondaryBoxPressed = false
    private var secondaryBox = -1
    private var currentBox = -1
    private fun isBoxChoosed(): Boolean {
        return (currentBox!=-1)
    }

    val list = listOf(
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black))
    )
    val secondaryList = listOf(
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
        mutableStateOf(Pair(0, Color.Black)),
    )
    var cSL = 0
    fun createSecondaryList(difficulty: Int){
        if (cSL!=0) return
        var count = 0
        while (count!=difficulty){
            secondaryList[count].value = MainList.mainList.random()
            count++
        }
        cSL++
    }
    fun createField(){
        var count = 0
        while (count!=25){
            list[count].value = MainList.mainList.random()
            count++
        }
    }

    private val wayList = mutableListOf<Int>()
    object MainList {
        val mainList = listOf(
            Pair(1, Color.Blue),
            Pair(2, Color.Red),
            Pair(3, Color.Green),
            Pair(4, Color.Yellow.copy(alpha = 0.8f)),
            Pair(5, MainColor)
        )
    }

    val score = mutableStateOf(0)
    var context: Context? = null
    init {
        createField()
    }
}