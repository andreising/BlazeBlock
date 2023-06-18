package com.andreisingeleytsev.blazeblock.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andreisingeleytsev.blazeblock.ui.screens.game_color_screen.GameColorScreen
import com.andreisingeleytsev.blazeblock.ui.screens.game_number_screen.GameNumberScreen
import com.andreisingeleytsev.blazeblock.ui.screens.main_screen.MainScreen
import com.andreisingeleytsev.blazeblock.ui.screens.result_screen.ResultScreen
import com.andreisingeleytsev.blazeblock.ui.screens.splash_screen.SplashScreen
import com.andreisingeleytsev.blazeblock.ui.utils.Routes

@Composable
fun BlazeBlockNavigation() {
    val navController = rememberNavController()
    val difficulty = remember {
        mutableStateOf(0)
    }
    NavHost(navController = navController, startDestination = Routes.SPLASH_SCREEN) {
        composable(Routes.SPLASH_SCREEN){
            SplashScreen(navController)
        }
        composable(Routes.MENU_SCREEN){
            MainScreen(difficulty, navController)
        }
        composable(Routes.GAME_COLOR_SCREEN){
            GameColorScreen(difficulty.value, navController)
        }

        composable(Routes.GAME_NUMBER_SCREEN){
            GameNumberScreen(difficulty.value, navController)
        }


        composable(Routes.RESULT_SCREEN){
            ResultScreen(navController)
        }

    }
}