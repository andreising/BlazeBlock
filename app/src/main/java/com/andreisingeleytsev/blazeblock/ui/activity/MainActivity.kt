package com.andreisingeleytsev.blazeblock.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.andreisingeleytsev.blazeblock.ui.navigation.BlazeBlockNavigation
import com.andreisingeleytsev.blazeblock.ui.screens.game_color_screen.GameColorScreen
import com.andreisingeleytsev.blazeblock.ui.screens.game_number_screen.GameNumberScreen
import com.andreisingeleytsev.blazeblock.ui.screens.main_screen.MainScreen
import com.andreisingeleytsev.blazeblock.ui.screens.splash_screen.SplashScreen
import com.andreisingeleytsev.blazeblock.ui.theme.BlazeBlockTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //my project bleat'
    //one
    //two
    //three
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlazeBlockNavigation()
        }
    }
}
