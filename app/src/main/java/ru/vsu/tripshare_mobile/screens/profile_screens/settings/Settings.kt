package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.MyRed
import ru.vsu.tripshare_mobile.ui.theme.darkGray24
import ru.vsu.tripshare_mobile.ui.theme.darkGray48
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white24

@Composable
fun Settings(user: UserModel, navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Настройки аккаунта",
            style = mint24
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(10.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ){

                Text(text = user.name, style = darkGray48)

                Image(
                    painterResource(id = if(user.avatarId == null) R.drawable.baseline_person else user.avatarId!!),
                    contentDescription = "author",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            }
        }

        Button(
            onClick = { navController.navigate("add_payment") },
            colors = ButtonDefaults.buttonColors(containerColor = MyMint),
            elevation = ButtonDefaults.buttonElevation(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = "Добавить способ оплаты", style = white24)
            }
        }

        Button(
            onClick = {
                val telegramUri = "https://t.me/TonyBambony07"

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(telegramUri)

                AppConfig.appContext.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            elevation = ButtonDefaults.buttonElevation(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = "Написать в техподдержку", style = darkGray24)
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    navController.navigate("first_auth")
                },
                colors = ButtonDefaults.buttonColors(containerColor = MyRed),
                elevation = ButtonDefaults.buttonElevation(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp, 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Выйти из аккаунта", style = white24)
                }
            }
        }
    }
}