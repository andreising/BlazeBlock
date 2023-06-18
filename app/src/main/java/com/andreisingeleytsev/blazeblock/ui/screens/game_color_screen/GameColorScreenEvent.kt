package com.andreisingeleytsev.blazeblock.ui.screens.game_color_screen

sealed class GameColorScreenEvent{
    data class OnSquarePressed(val count: Int): GameColorScreenEvent()
    object OnBack: GameColorScreenEvent()
}
