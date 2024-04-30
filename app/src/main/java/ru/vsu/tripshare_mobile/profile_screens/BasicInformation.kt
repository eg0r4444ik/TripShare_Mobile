package ru.vsu.tripshare_mobile.profile_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.darkGray36
import ru.vsu.tripshare_mobile.ui.theme.darkGray48
import ru.vsu.tripshare_mobile.ui.theme.mint18
import ru.vsu.tripshare_mobile.ui.theme.white18

@Composable
fun BasicInformation(user: UserModel, person: UserModel, navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ){

        Row {

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                if(user.name.length <= 6) {
                    Text(text = user.name, style = darkGray48)
                }else{
                    Text(text = user.name, style = darkGray36)
                }
                Text(text = user.phone, style = darkGray18)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                user.imageId?.let { painterResource(id = it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = "image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }


        if(user.id == person.id) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomStart,
            ) {
                Column {
                    Text(
                        text = "Изменить фото профиля",
                        style = mint18,
                        modifier = Modifier.clickable {
                            navController.navigate("")
                        })
                    Text(
                        text = "Редактировать информацию о себе",
                        style = mint18,
                        modifier = Modifier.clickable {
                            navController.navigate("edit_info")
                        })
                    Text(
                        text = "Настройки аккаунта",
                        style = blue18,
                        modifier = Modifier.clickable {
                            navController.navigate("settings")
                        })
                }
            }
        }else{
            Box(
                modifier = Modifier.fillMaxSize().padding(10.dp)
            ) {
                Button(
                    //todo добавить добавление чата
                    onClick = { navController.navigate("chat") },
                    colors = ButtonDefaults.buttonColors(containerColor = MyBlue),
                    modifier = Modifier.fillMaxWidth().height(50.dp).padding(20.dp, 0.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Написать", style = white18)
                    }
                }
            }
        }
    }
}