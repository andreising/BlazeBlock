package com.andreisingeleytsev.blazeblock.ui.screens.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.blazeblock.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {
    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: MainScreenEvent){
        when(event) {
            is MainScreenEvent.OnGameChoose -> {
                startMenu.value = false
                gameIndex = event.index
            }
            is MainScreenEvent.OnBack -> {
                startMenu.value = true
            }
            is MainScreenEvent.OnDifficultyChoose -> {
                sendUIEvent(UIEvents.SetDifficulty(event.index))
                if (gameIndex==0) sendUIEvent(UIEvents.OnNavigate(Routes.GAME_COLOR_SCREEN))
                else sendUIEvent(UIEvents.OnNavigate(Routes.GAME_NUMBER_SCREEN))
            }
        }
    }

    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
    val startMenu = mutableStateOf(true)
    var gameIndex = 0

}