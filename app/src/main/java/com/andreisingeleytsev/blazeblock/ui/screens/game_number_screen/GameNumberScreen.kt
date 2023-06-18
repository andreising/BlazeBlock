package com.andreisingeleytsev.blazeblock.ui.screens.game_number_screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.andreisingeleytsev.blazeblock.ui.theme.SecondaryColor
import com.andreisingeleytsev.blazeblock.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.Fonts
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents


@Composable
fun GameNumberScreen(state: Int, navHostController: NavHostController, viewModel: GameNumberScreenViewModel = hiltViewModel()){
    viewModel.createSecondaryList(state)
    viewModel.context = LocalContext.current
    val sharedPreferences = LocalContext.current.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    val myData = sharedPreferences?.getInt("key", 0)
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvents.OnNavigate -> {
                    navHostController.navigate(it.route){
                        popUpTo(Routes.GAME_NUMBER_SCREEN){
                            inclusive = true
                        }
                    }
                }
                is UIEvents.BackStack -> {
                    navHostController.popBackStack()
                }

                else -> {}
            }
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MainColor), contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(id = R.drawable.game_bg), contentDescription = null,
            Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
        )
        Column(
            Modifier.padding(start = 11.dp, end = 11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Score",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = Fonts.customFontFamily,
                    color = Color.White
                )
            )
            Text(
                text = viewModel.score.value.toString(),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = Fonts.customFontFamily,
                    color = Color.White
                )
            )
            val gridSize = 5
            var count = 0
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Transparent),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .background(SecondaryColor),
                ) {
                    repeat(gridSize) { row ->
                        Row(Modifier.background(Color.Transparent)) {
                            repeat(gridSize) { col ->
                                val thisCount = count
                                if (count < 24) count++
                                else count = 0
                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .background(viewModel.list[thisCount].value.second)
                                        .size(50.dp)
                                        .clickable {
                                            viewModel.onEvent(
                                                GameNumberScreenEvent.OnFieldBoxPressed(
                                                    thisCount
                                                )
                                            )
                                        },
                                    contentAlignment = Alignment.Center

                                ) {
                                    Text(
                                        text = viewModel.list[thisCount].value.first.toString(),
                                        style = TextStyle(
                                            fontSize = 32.sp,
                                            fontFamily = Fonts.customFontFamily,
                                            color = Color.White
                                        )
                                    )
                                }
                            }
                        }
                        if (row < gridSize - 1) {
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
            }

            LazyRow(){

                items(viewModel.secondaryList){

                    if (it.value.second!= Color.Black) {
                        Log.d("tag", viewModel.secondaryList.indexOf(it).toString())
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .background(it.value.second)
                                .size(50.dp)
                                .clickable {

                                    viewModel.onEvent(GameNumberScreenEvent.OnSecondaryBoxPressed(viewModel.secondaryList.indexOf(it)))
                                },
                            contentAlignment = Alignment.Center

                        ) {
                            Text(
                                text = it.value.first.toString(),
                                style = TextStyle(
                                    fontSize = 32.sp,
                                    fontFamily = Fonts.customFontFamily,
                                    color = Color.White
                                )
                            )
                        }
                    }

                }
            }
        }


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
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.main_screen_back), contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(55.dp)
                    .clickable {
                        viewModel.onEvent(GameNumberScreenEvent.OnBack)
                    })
            Spacer(modifier = Modifier.width(50.dp))
            Image(painter = painterResource(id = R.drawable.help_button), contentDescription = null)
            Spacer(modifier = Modifier.width(10.dp))
            Image(painter = painterResource(id = R.drawable.question_mark), contentDescription = null)
        }
    }
}