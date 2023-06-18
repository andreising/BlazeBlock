package com.andreisingeleytsev.sportgameapp.ui.utils

sealed class UIEvents(){
    data class OnNavigate(val route: String): UIEvents()
    data class SetDifficulty(val difficulty: Int): UIEvents()
    object BackStack: UIEvents()
}
