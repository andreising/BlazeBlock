package com.andreisingeleytsev.blazeblock.ui.screens.splash_screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.andreisingeleytsev.blazeblock.R
import com.andreisingeleytsev.blazeblock.ui.theme.MainColor
import com.andreisingeleytsev.blazeblock.ui.utils.Routes
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(3000) // Ожидание 3 секунды
        navController.navigate(Routes.MENU_SCREEN){
            popUpTo(Routes.SPLASH_SCREEN){
                inclusive = true
            }
        }
    }
    var target by remember {
        mutableStateOf(0.dp)
    }
    val dotOffset by animateDpAsState(
        targetValue = target,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    )
    Box(modifier = Modifier.fillMaxSize().background(MainColor), contentAlignment = Alignment.Center){
        Image(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.splash_background), contentDescription = null, contentScale = ContentScale.FillBounds)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.splash_waiting_element), contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .offset(x = 0.dp, y = dotOffset / 2)
                )
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.splash_waiting_element), contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = 0.dp, y = -dotOffset / 2))
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.splash_waiting_element), contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = 0.dp, y = -1.5*dotOffset))
        }
    }
    target = 10.dp
}