package ru.vsu.tripshare_mobile.screens.registration_screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.tripshare_mobile.api.RetrofitApi
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyLightGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white18
import ru.vsu.tripshare_mobile.api.dto.RegistrationRequestDTO
import ru.vsu.tripshare_mobile.api.dto.RegistrationResponseDTO
import ru.vsu.tripshare_mobile.services.AuthService

@Composable
fun RegistrationScreen(phoneNumber: String, navController: NavController){

    val state = rememberScrollState()
    // todo Добавить валидацию полей
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(state)
    ) {
        Text(text = "Создание аккаунта", style = mint24, modifier = Modifier.padding(10.dp, 0.dp))

        var pass1 by remember { mutableStateOf("") }
        var pass2 by remember { mutableStateOf("") }
        TextField(
            value = pass1,
            onValueChange = { newText ->
                pass1 = newText
            },
            label = { Text("Пароль") },
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
            value = pass2,
            onValueChange = { newText ->
                pass2 = newText
            },
            label = { Text("Повторите пароль") },
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

        var name by remember { mutableStateOf("") }
        var surname by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf(phoneNumber) }
        var email by remember { mutableStateOf("") }
        var birthday by remember { mutableStateOf("") }

        TextField(
            value = name,
            onValueChange = { newText ->
                name = newText
            },
            label = { Text("Имя") },
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
            value = surname,
            onValueChange = { newText ->
                surname = newText
            },
            label = { Text("Фамилия") },
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
            value = phone,
            onValueChange = { newText ->
                phone = newText
            },
            label = { Text("Телефон") },
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
            value = email!!,
            onValueChange = { newText ->
                email = newText
            },
            label = { Text("Почта") },
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
            value = birthday,
            onValueChange = { newText ->
                birthday = newText
            },
            label = { Text("Дата рождения") },
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

        var musicPreferences by remember { mutableStateOf("") }
        var talkativeness by remember { mutableStateOf("") }
        var attitudeTowardsSmoking by remember { mutableStateOf("") }
        var attitudeTowardsAnimals by remember { mutableStateOf("") }
        var info by remember { mutableStateOf("") }

        TextField(
            value = musicPreferences!!,
            onValueChange = { newText ->
                musicPreferences = newText
            },
            label = { androidx.compose.material.Text("Любимые музыкальные жанры") },
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
            value = talkativeness!!,
            onValueChange = { newText ->
                talkativeness = newText
            },
            label = { androidx.compose.material.Text("Степень разговорчивости") },
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
            label = { androidx.compose.material.Text("Отношение к курению") },
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
            label = { androidx.compose.material.Text("Отношение к животным в поездке") },
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
            value = info!!,
            onValueChange = { newText ->
                info = newText
            },
            label = { androidx.compose.material.Text("Дополнительная информация") },
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

        Button(
            onClick = {
                if(pass1.equals(pass2)) {
                    val user = RegistrationRequestDTO(
                        phone,
                        name,
                        surname,
                        email,
                        birthday,
                        musicPreferences,
                        info,
                        if(talkativeness.equals(""))  null else talkativeness.toInt(),
                        if(attitudeTowardsSmoking.equals(""))  null else attitudeTowardsSmoking.toInt(),
                        if(attitudeTowardsAnimals.equals(""))  null else attitudeTowardsAnimals.toInt(),
                        pass1,
                        null
                    )

                    AuthService.registerUser(user)
                    navController.navigate("first_auth")
                }
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
                Text(text = "Сохранить изменения", style = white18)
            }
        }
    }
}