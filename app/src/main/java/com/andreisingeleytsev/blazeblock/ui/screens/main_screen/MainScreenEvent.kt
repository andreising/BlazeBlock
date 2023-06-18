package com.andreisingeleytsev.blazeblock.ui.screens.main_screen

sealed class MainScreenEvent{
    data class OnGameChoose(val index: Int): MainScreenEvent()
    data class OnDifficultyChoose(val index: Int): MainScreenEvent()
    object OnBack: MainScreenEvent()
}
