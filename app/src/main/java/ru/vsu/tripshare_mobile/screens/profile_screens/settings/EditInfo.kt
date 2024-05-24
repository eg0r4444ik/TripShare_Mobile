package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun EditInfo(user: UserModel, navController: NavController){

    //todo добавить валидацию текстовых полей
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Редактирование", style = mint24, modifier = Modifier.padding(10.dp, 0.dp))
        Text(text = "информации о себе", style = mint24, modifier = Modifier.padding(10.dp, 0.dp))

        var name by remember { mutableStateOf(user.name) }
        var surname by remember { mutableStateOf(user.surname) }
        var phone by remember { mutableStateOf(user.phone) }
        var email by remember { mutableStateOf(if(user.email == null) "" else user.email!!) }
        var birthday by remember { mutableStateOf(if(user.birthday == null) "" else user.birthday!!) }

        TextField(
            value = name,
            onValueChange = { newText ->
                name = newText
            },
            label = { Text("Имя") },
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
            value = surname,
            onValueChange = { newText ->
                surname = newText
            },
            label = { Text("Фамилия") },
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
            value = phone,
            onValueChange = { newText ->
                phone = newText
            },
            label = { Text("Телефон") },
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
            value = email!!,
            onValueChange = { newText ->
                email = newText
            },
            label = { Text("Почта") },
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
            value = birthday,
            onValueChange = { newText ->
                birthday = newText
            },
            label = { Text("Дата рождения") },
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
                user.name = name
                user.surname = surname
                user.email = email
                user.phone = phone
                user.birthday = birthday
                navController.navigate("profile_screen")
                      },
            colors = ButtonDefaults.buttonColors(containerColor = MyMint),
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(20.dp, 0.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(text = "Сохранить изменения", style = white18)
            }
        }
    }
}