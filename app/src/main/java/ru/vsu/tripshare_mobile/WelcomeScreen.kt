package ru.vsu.tripshare_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.white24
import ru.vsu.tripshare_mobile.ui.theme.white32
import ru.vsu.tripshare_mobile.ui.theme.white36

@Composable
fun WelcomeScreen(navController: NavController){
    val brush = Brush.linearGradient(listOf(Color(0xFF4AFEB9), Color(0xFF2D9FCE)))
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "person",
                modifier = Modifier.size(200.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Добро пожаловать", style = white32,
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "в Trip Share!", style = white32,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { navController.navigate("first_registration") },
                colors = ButtonDefaults.buttonColors(containerColor = MyBlue),
                modifier = Modifier
                    .height(70.dp)
                    .width(380.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Продолжить", style = white24)
                }
            }
        }
    }
}