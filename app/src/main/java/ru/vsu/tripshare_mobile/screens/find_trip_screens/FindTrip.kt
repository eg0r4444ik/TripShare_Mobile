package ru.vsu.tripshare_mobile.screens.find_trip_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyLightGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint36
import ru.vsu.tripshare_mobile.ui.theme.white18

@Composable
fun FindTrip(person: UserModel, navController: NavController){

    val state = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .verticalScroll(state)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Поездки по самым", style = mint36)
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "низким ценам", style = mint36)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp) ,
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ){
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                // todo добавить валидацию текстовых полей

                var addressFrom by remember { mutableStateOf("") }
                var addressTo by remember { mutableStateOf("") }
                var day by remember { mutableStateOf("") }
                var time by remember { mutableStateOf("") }
                var participantsCount by remember { mutableStateOf("") }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "location",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    TextField(
                        value = addressFrom,
                        onValueChange = { newText ->
                            addressFrom = newText
                        },
                        label = { Text("Откуда") },
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
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "location",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    TextField(
                        value = addressTo,
                        onValueChange = { newText ->
                            addressTo = newText
                        },
                        label = { Text("Куда") },
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
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = "calendar",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    TextField(
                        value = day,
                        onValueChange = { newText ->
                            day = newText
                        },
                        label = { Text("Дата") },
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
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.clock),
                            contentDescription = "clock",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    TextField(
                        value = time,
                        onValueChange = { newText ->
                            time = newText
                        },
                        label = { Text("Время") },
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
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = "person",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    TextField(
                        value = participantsCount,
                        onValueChange = { newText ->
                            participantsCount = newText
                        },
                        label = { Text("Количество пассажиров") },
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
                }

                val passengersCount = remember { mutableStateOf(false) }
                val smoking = remember { mutableStateOf(false) }
                val freeTrunk = remember { mutableStateOf(false) }
                val animals = remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    TripFacilities(
                        text = "Максимум 2 пассажира на заднем сидении",
                        state = passengersCount
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    TripFacilities(
                        text = "Можно курить",
                        state = smoking
                    )

                    TripFacilities(
                        text = "Свободный багажник",
                        state = freeTrunk
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    TripFacilities(
                        text = "Можно перевозить животных",
                        state = animals
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        //todo добавить отправку данных на бекенд
                        onClick = { navController.navigate("list_of_trips") },
                        colors = ButtonDefaults.buttonColors(containerColor = MyMint),
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Поиск", style = white18)
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun TripFacilities(text: String, state: MutableState<Boolean>){
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Checkbox(
            checked = state.value,
            onCheckedChange = { state.value = it }
        )
        Text(
            text,
            style = darkGray14,
            modifier = Modifier.padding(4.dp)
        )
    }
}
