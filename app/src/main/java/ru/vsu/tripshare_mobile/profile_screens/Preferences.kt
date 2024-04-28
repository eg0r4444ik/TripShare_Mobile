package ru.vsu.tripshare_mobile.profile_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.darkGray36
import ru.vsu.tripshare_mobile.ui.theme.white18

@Composable
fun Preferences(user: UserModel, person: UserModel, navController: NavController){
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
        Column(
            modifier = Modifier.padding(10.dp, 5.dp),
        ){

            if(person.userId == user.userId) {
                Text(text = "Мои", style = darkGray36)
                Text(text = "предпочтения", style = darkGray36)
            }else{
                Text(text = "Предпочтения", style = darkGray36)
                Text(text = "пользователя", style = darkGray36)
            }

            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.Start
            ){
                Preference("Музыкальные предпочтения", user.musicPreferences)
                Preference("Общительность", user.sociability)
                Preference("Отношение к курению", user.attitudeTowardsSmoking)
                Preference("Отношение к животным", user.attitudeTowardsAnimals)
                Preference("Дополнительная информация", user.info)
            }

            if(person.userId == user.userId) {
                Button(
                    onClick = { navController.navigate("edit_preferences") },
                    colors = ButtonDefaults.buttonColors(containerColor = MyMint),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .padding(20.dp, 0.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Редактировать ", style = white18)
                        Text(text = "мои предпочтения", style = white18)
                    }
                }
            }
        }
    }
}

@Composable
private fun Preference(preferenceName: String, preference: String?){
    if(preference != null) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "*" + preferenceName, style = blue18)
            Text(text = " " + preference, style = darkGray18)
        }
    }
}