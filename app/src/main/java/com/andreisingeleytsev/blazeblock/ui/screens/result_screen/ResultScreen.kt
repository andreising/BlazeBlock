package com.andreisingeleytsev.blazeblock.ui.screens.result_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andreisingeleytsev.blazeblock.ui.theme.MainColor
import com.andreisingeleytsev.blazeblock.ui.theme.MainColor2
import com.andreisingeleytsev.blazeblock.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.Fonts

@Composable
fun ResultScreen(navHostController: NavHostController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MainColor),
        contentAlignment = Alignment.Center){
            Column(modifier = Modifier
                .padding(22.dp)
                .background(MainColor2), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "level passed",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = Fonts.customFontFamily,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 28.dp, bottom = 28.dp)
                )
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(MainColor.copy(alpha = 0.95f))
                        .padding(bottom = 54.dp)
                ) {
                    Column(Modifier.padding(60.dp)) {
                        OutlinedButton(
                            onClick = {
                                navHostController.navigate(Routes.GAME_COLOR_SCREEN) {
                                    popUpTo(Routes.RESULT_SCREEN) {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MainColor2
                            )
                        ) {
                            Text(
                                text = "again",
                                style = TextStyle(
                                    fontSize = 32.sp,
                                    fontFamily = Fonts.customFontFamily,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(44.dp))
                        OutlinedButton(
                            onClick = { navHostController.popBackStack() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MainColor2
                            )
                        ) {
                            Text(
                                text = "menu",
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