package com.andreisingeleytsev.blazeblock.ui.screens.main_screen

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.blazeblock.R
import com.andreisingeleytsev.blazeblock.ui.theme.MainColor
import com.andreisingeleytsev.sportgameapp.ui.utils.Fonts
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents


@Composable
fun MainScreen(state: MutableState<Int>, navHostController: NavHostController, viewModel: MainScreenViewModel = hiltViewModel()){
    val sharedPreferences = LocalContext.current.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    val myData = sharedPreferences?.getInt("key", 0)
    BackHandler {
        if (!viewModel.startMenu.value) viewModel.startMenu.value=true
        else navHostController.popBackStack()
    }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvents.OnNavigate -> {
                    navHostController.navigate(it.route)
                }
                is UIEvents.SetDifficulty -> {
                    state.value = it.difficulty
                }

                else -> {}
            }
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MainColor), contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.main_screen_bg), contentDescription = null,
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)
        if (viewModel.startMenu.value) GameMenu()
        else LevelMenu()


    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
        Row(
            Modifier.padding(top = 17.dp, end = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_point), contentDescription = null,
                contentScale = ContentScale.FillHeight, modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = myData.toString(),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = Fonts.customFontFamily,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
fun LevelMenu(viewModel: MainScreenViewModel = hiltViewModel()){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(
            Modifier
                .padding(start = 40.dp, end = 40.dp)
                .fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.lvl_first),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(MainScreenEvent.OnDifficultyChoose(1))
                    })
            Spacer(modifier = Modifier.height(30.dp))
            Image(painter = painterResource(id = R.drawable.lvl_second),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(MainScreenEvent.OnDifficultyChoose(2))
                    })
            Spacer(modifier = Modifier.height(30.dp))
            Image(painter = painterResource(id = R.drawable.lvl_third),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(MainScreenEvent.OnDifficultyChoose(3))
                    })
            Spacer(modifier = Modifier.height(30.dp))
            Image(painter = painterResource(id = R.drawable.lvl_fourth),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(MainScreenEvent.OnDifficultyChoose(4))
                    })
            Spacer(modifier = Modifier.height(30.dp))
            Image(painter = painterResource(id = R.drawable.lvl_fifth),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(MainScreenEvent.OnDifficultyChoose(5))
                    })
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        Row(
            Modifier
                .padding(bottom = 30.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
            IconButton(modifier = Modifier.width(100.dp), onClick = {
                viewModel.onEvent(MainScreenEvent.OnBack)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.main_screen_back),
                    contentDescription = null
                )
            }
            IconButton(modifier = Modifier
                .height(100.dp)
                .padding(end = 10.dp), onClick = {

            }) {
                Image(
                    painter = painterResource(id = R.drawable.main_screen_random),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }

}

@Composable
fun GameMenu(viewModel: MainScreenViewModel = hiltViewModel()) {
    Column(Modifier.padding(start = 40.dp, end = 40.dp)) {
        Image(painter = painterResource(id = R.drawable.first_game), contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.onEvent(MainScreenEvent.OnGameChoose(0))
                }, contentScale = ContentScale.FillWidth)
        Spacer(modifier = Modifier.height(40.dp))
        Image(painter = painterResource(id = R.drawable.second_game), contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.onEvent(MainScreenEvent.OnGameChoose(1))
                }, contentScale = ContentScale.FillWidth)
    }
}