package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.config.AppConfig
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
        var sociability by remember { mutableStateOf(if(user.talkativeness == null) "" else user.talkativeness.toString()) }
        var attitudeTowardsSmoking by remember { mutableStateOf(if(user.attitudeTowardsSmoking == null) "" else user.attitudeTowardsSmoking.toString()) }
        var attitudeTowardsAnimals by remember { mutableStateOf(if(user.attitudeTowardsAnimals == null) "" else user.attitudeTowardsAnimals.toString()) }
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

        var sociabilityError by remember { mutableStateOf(false) }

        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = sociability,
                onValueChange = { newText ->
                    val number = newText.toIntOrNull()
                    if (number != null && number in 0..10) {
                        sociability = newText
                        sociabilityError = false
                    } else {
                        if (newText.isEmpty()) {
                            sociability = newText
                        }
                        sociabilityError = true
                    }
                },
                label = { Text("Степень разговорчивости (0-10)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = sociabilityError,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                textStyle = darkGray18,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MyLightGray,
                    focusedIndicatorColor = MyDarkGray,
                    unfocusedIndicatorColor = MyDarkGray
                ),
                shape = RoundedCornerShape(15.dp)
            )

            if (sociabilityError) {
                Text(
                    text = "Введите число от 0 до 10",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(10.dp, 5.dp)
                )
            }
        }

        var attitudeTowardsSmokingError by remember { mutableStateOf(false) }

        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = attitudeTowardsSmoking,
                onValueChange = { newText ->
                    val number = newText.toIntOrNull()
                    if (number != null && number in 0..10) {
                        attitudeTowardsSmoking = newText
                        attitudeTowardsSmokingError = false
                    } else {
                        if (newText.isEmpty()) {
                            attitudeTowardsSmoking = newText
                        }
                        attitudeTowardsSmokingError = true
                    }
                },
                label = { Text("Отношение к курению (0-10)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = attitudeTowardsSmokingError,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                textStyle = darkGray18,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MyLightGray,
                    focusedIndicatorColor = MyDarkGray,
                    unfocusedIndicatorColor = MyDarkGray
                ),
                shape = RoundedCornerShape(15.dp)
            )

            if (attitudeTowardsSmokingError) {
                Text(
                    text = "Введите число от 0 до 10",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(10.dp, 5.dp)
                )
            }
        }

        var attitudeTowardsAnimalsError by remember { mutableStateOf(false) }

        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = attitudeTowardsAnimals,
                onValueChange = { newText ->
                    val number = newText.toIntOrNull()
                    if (number != null && number in 0..10) {
                        attitudeTowardsAnimals = newText
                        attitudeTowardsAnimalsError = false
                    } else {
                        if (newText.isEmpty()) {
                            attitudeTowardsAnimals = newText
                        }
                        attitudeTowardsAnimalsError = true
                    }
                },
                label = { Text("Отношение к животным (0-10)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = attitudeTowardsAnimalsError,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                textStyle = darkGray18,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MyLightGray,
                    focusedIndicatorColor = MyDarkGray,
                    unfocusedIndicatorColor = MyDarkGray
                ),
                shape = RoundedCornerShape(15.dp)
            )

            if (attitudeTowardsAnimalsError) {
                Text(
                    text = "Введите число от 0 до 10",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(10.dp, 5.dp)
                )
            }
        }

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
                if(!sociability.equals("")) {
                    user.talkativeness = sociability.toInt()
                }
                if(!attitudeTowardsSmoking.equals("")) {
                    user.attitudeTowardsSmoking = attitudeTowardsSmoking.toInt()
                }
                if(!attitudeTowardsAnimals.equals("")) {
                    user.attitudeTowardsAnimals = attitudeTowardsAnimals.toInt()
                }
                user.info = info
                AppConfig.currentUser = user
//                CoroutineScope(Dispatchers.Main).launch {
//                    UserService.updateMe()
//                }
//                CoroutineScope(Dispatchers.Main).launch {
//                    UserService.updateMe()
//                }
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