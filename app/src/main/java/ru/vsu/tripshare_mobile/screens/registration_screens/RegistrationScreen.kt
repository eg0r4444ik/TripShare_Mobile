package ru.vsu.tripshare_mobile.screens.registration_screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.api.dto.users.RegistrationDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.services.AuthService
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyLightGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white18
import java.util.Calendar
import java.util.Date

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(phoneNumber: String, navController: NavController){

    val state = rememberScrollState()
    // todo Добавить валидацию полей
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state)
    ) {
        Text(text = "Создание аккаунта", style = mint24, modifier = Modifier.padding(10.dp, 0.dp))

        var name by remember { mutableStateOf("") }
        var surname by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf(phoneNumber) }
        var email by remember { mutableStateOf("") }

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
            value = email,
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

        val context = AppConfig.appContext

        val year: Int
        val month: Int
        val day: Int

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.time = Date()

        val date = remember { mutableStateOf("") }
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                date.value = String.format("%02d.%02d.%s", dayOfMonth, month, year)
            }, year, month, day
        )

        Row(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Text(text = "Дата рождения: \n${date.value}", style = darkGray18)
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = {
                datePickerDialog.show()
            },
                colors = ButtonDefaults.buttonColors(containerColor = MyBlue),
            ) {
                Text(text = "Выбрать дату", style = white18)
            }
        }

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

        Button(
            onClick = {
                val registrationEvent = "{\"button_clicked\":\"registration\"}"
                AppMetrica.reportEvent(
                    "Registration event",
                    registrationEvent
                )
                if(!pass1.equals(pass2)) {
                    Toast.makeText(
                        AppConfig.appContext,
                        "Пароли должны совпадать",
                        Toast.LENGTH_SHORT
                    ).show()
                }else if(pass1.equals("")){
                    Toast.makeText(
                        AppConfig.appContext,
                        "Заполните пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }else if(name.equals("") || surname.equals("")){
                    Toast.makeText(
                        AppConfig.appContext,
                        "Заполните имя и фамилию",
                        Toast.LENGTH_SHORT
                    ).show()
                }else if(!isPhoneNumber(phone)){
                    Toast.makeText(
                        AppConfig.appContext,
                        "Введите корректный номер телефона",
                        Toast.LENGTH_SHORT
                    ).show()
                }else if(!isEmail(email)){
                    Toast.makeText(
                        AppConfig.appContext,
                        "Введите корректный email",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    val user = RegistrationDTO(
                        phone,
                        name,
                        surname,
                        email,
                        date.value,
                        "",
                        "",
                        null,
                        null,
                        null,
                        pass1,
                        null
                    )

                    CoroutineScope(Dispatchers.Main).launch {
                        AuthService.registerUser(user)
                    }
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

private fun isPhoneNumber(input: String): Boolean {
    val phonePattern = "^(\\+\\d{1,3})?\\d{10,11}\$".toRegex()
    return phonePattern.matches(input)
}

private fun isEmail(input: String): Boolean {
    val phonePattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$".toRegex()
    return phonePattern.matches(input)
}