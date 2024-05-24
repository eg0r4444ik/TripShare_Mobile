package ru.vsu.tripshare_mobile.screens.registration_screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.tripshare_mobile.api.RetrofitApi
import ru.vsu.tripshare_mobile.api.dto.TokenDTO
import ru.vsu.tripshare_mobile.api.dto.UserDTO
import ru.vsu.tripshare_mobile.services.AuthService
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint26
import ru.vsu.tripshare_mobile.ui.theme.mintNormal18
import ru.vsu.tripshare_mobile.ui.theme.white24

@Composable
fun FirstAuthScreen(navController: NavController){
    Column (
        modifier = Modifier.fillMaxSize().background(Color.White)
    ){
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f),
            contentAlignment = Alignment.BottomCenter
        ){
            Text(text = "Вход в Trip Share", style = mint26)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            var phone by remember { mutableStateOf("+7") }
            TextField(
                value = phone,
                onValueChange = { newText ->
                    phone = newText
                },
                label = { androidx.compose.material.Text("Телефон") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(10.dp),
                textStyle = darkGray18,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = MyDarkGray,
                    unfocusedIndicatorColor = MyDarkGray
                )
            )

            Button(
                onClick = {
                    navController.navigate("second_auth/${phone}")
                          },
                //todo проверить что пользователь зарегистрирован
//                onClick = { navController.navigate("registration/${phone}") },
                colors = ButtonDefaults.buttonColors(containerColor = MyMint),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(10.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material.Text(text = "Продолжить", style = white24)
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Зарегистрироваться", style = mintNormal18, modifier = Modifier.clickable { navController.navigate("registration/${phone}") })
            }
        }
    }
}