package ru.vsu.tripshare_mobile.profile_screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyLightGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white18

@Composable
fun EditPreferences(user: UserModel, navController: NavController){
    val state = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(state)
    ) {
        Text(text = "Редактирование", style = mint24, modifier = Modifier.padding(10.dp, 0.dp))
        Text(text = "предпочтений", style = mint24, modifier = Modifier.padding(10.dp, 0.dp))

        var musicPreferences by remember { mutableStateOf(if(user.musicPreferences == null) "" else user.musicPreferences) }
        var sociability by remember { mutableStateOf(if(user.sociability == null) "" else user.sociability) }
        var attitudeTowardsSmoking by remember { mutableStateOf(if(user.attitudeTowardsSmoking == null) "" else user.attitudeTowardsSmoking) }
        var attitudeTowardsAnimals by remember { mutableStateOf(if(user.attitudeTowardsAnimals == null) "" else user.attitudeTowardsAnimals) }
        var info by remember { mutableStateOf(if(user.info == null) "" else user.info) }

        TextField(
            value = musicPreferences!!,
            onValueChange = { newText ->
                musicPreferences = newText
            },
            label = { Text("Любимые музыкальные жанры") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textStyle = darkGray18,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MyLightGray,
                focusedIndicatorColor = MyDarkGray,
                unfocusedIndicatorColor = MyDarkGray
            ),
            shape = RoundedCornerShape(15.dp)
        )

        TextField(
            value = sociability!!,
            onValueChange = { newText ->
                sociability = newText
            },
            label = { Text("Степень разговорчивости") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textStyle = darkGray18,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MyLightGray,
                focusedIndicatorColor = MyDarkGray,
                unfocusedIndicatorColor = MyDarkGray
            ),
            shape = RoundedCornerShape(15.dp)
        )

        TextField(
            value = attitudeTowardsSmoking!!,
            onValueChange = { newText ->
                attitudeTowardsSmoking = newText
            },
            label = { Text("Отношение к курению") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textStyle = darkGray18,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MyLightGray,
                focusedIndicatorColor = MyDarkGray,
                unfocusedIndicatorColor = MyDarkGray
            ),
            shape = RoundedCornerShape(15.dp)
        )

        TextField(
            value = attitudeTowardsAnimals!!,
            onValueChange = { newText ->
                attitudeTowardsAnimals = newText
            },
            label = { Text("Отношение к животным в поездке") },
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            textStyle = darkGray18,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MyLightGray,
                focusedIndicatorColor = MyDarkGray,
                unfocusedIndicatorColor = MyDarkGray
            ),
            shape = RoundedCornerShape(15.dp)
        )

        TextField(
            value = info!!,
            onValueChange = { newText ->
                info = newText
            },
            label = { Text("Дополнительная информация") },
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            textStyle = darkGray18,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MyLightGray,
                focusedIndicatorColor = MyDarkGray,
                unfocusedIndicatorColor = MyDarkGray
            ),
            shape = RoundedCornerShape(15.dp)
        )

        Button(
            onClick = {
                user.musicPreferences = musicPreferences
                user.sociability = sociability
                user.attitudeTowardsSmoking = attitudeTowardsSmoking
                user.attitudeTowardsAnimals = attitudeTowardsAnimals
                user.info = info
                navController.navigate("profile_screen")
            },
            colors = ButtonDefaults.buttonColors(containerColor = MyMint),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(20.dp, 0.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(text = "Сохранить изменения", style = white18)
            }
        }
    }
}