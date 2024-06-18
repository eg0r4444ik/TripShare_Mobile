package ru.vsu.tripshare_mobile.screens.registration_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.white14

@Composable
fun AuthorizationRequestScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Этот экран недоступен", style = darkGray18)
                Text(text = "Для доступа войдите в свой аккаунт", style = darkGray18)

                Button(
                    onClick = { navController.navigate("first_auth") },
                    colors = ButtonDefaults.buttonColors(containerColor = MyBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(20.dp, 0.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Войти в аккаунт", style = white14)
                    }
                }
            }
        }
    }
}