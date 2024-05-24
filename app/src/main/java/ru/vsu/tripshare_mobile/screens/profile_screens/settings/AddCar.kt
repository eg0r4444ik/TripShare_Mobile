package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import ru.vsu.tripshare_mobile.models.CarModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyLightGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white18

@Composable
fun AddCar(user: UserModel, navController: NavController){

    //todo добавить валидацию текстовых полей
    val state = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state)
    ) {
        Text(text = "Добавление авто", style = mint24, modifier = Modifier.padding(10.dp, 0.dp))

        Button(
            modifier = Modifier.height(120.dp).width(160.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MyLightGray),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Добавить фото", style = darkGray18)
        }

//        LazyRow(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(10.dp)
//        ) {
//            Button(
//                modifier = Modifier.height(120.dp).width(160.dp),
//                onClick = { /*TODO*/ }
//            ) {
//                Text(text = "Добавить фото")
//            }
//            itemsIndexed(user.cars!!) { _, item ->
//                Column(
//                    modifier = Modifier
//                        .padding(10.dp, 10.dp),
//                    verticalArrangement = Arrangement.SpaceBetween,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(
//                        painterResource(id = item.imageIds.get(0)),
//                        contentDescription = "car",
//                        modifier = Modifier.size(160.dp, 120.dp)
//                    )
//                    androidx.compose.material3.Text(text = item.model + " " + item.brand, style = darkGray14)
//                }
//            }
//        }

        var imageIds = null
        var brand by remember { mutableStateOf("") }
        var model by remember { mutableStateOf("") }
        var color by remember { mutableStateOf("") }
        var manufactureYear by remember { mutableStateOf("") }

        TextField(
            value = brand,
            onValueChange = { newText ->
                brand = newText
            },
            label = { Text("Марка авто") },
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
            value = model,
            onValueChange = { newText ->
                model = newText
            },
            label = { Text("Модель") },
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
            value = color,
            onValueChange = { newText ->
                color = newText
            },
            label = { Text("Цвет") },
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
            value = manufactureYear,
            onValueChange = { newText ->
                manufactureYear = newText
            },
            label = { Text("Год выпуска") },
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
                val car = CarModel(brand, model, color, manufactureYear.toInt(), imageIds)
                if(user.cars == null){
                    user.cars = mutableListOf(car)
                }else {
                    user.cars!!.add(car)
                }
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
                androidx.compose.material3.Text(text = "Добавить автомобиль", style = white18)
            }
        }
    }
}