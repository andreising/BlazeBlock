package com.andreisingeleytsev.blazeblock.ui.screens.game_number_screen

import androidx.compose.ui.graphics.Color
import com.andreisingeleytsev.blazeblock.ui.screens.game_color_screen.GameColorScreenEvent

sealed class GameNumberScreenEvent{
    data class OnFieldBoxPressed(val count: Int): GameNumberScreenEvent()
    data class OnSecondaryBoxPressed(val index: Int): GameNumberScreenEvent()
    object OnBack: GameNumberScreenEvent()
}
