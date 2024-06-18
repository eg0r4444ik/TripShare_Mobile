package ru.vsu.tripshare_mobile.screens.add_trip_screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.api.dto.trips.TripDTO
import ru.vsu.tripshare_mobile.api.dto.users.RegistrationDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.StopModel
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.services.AuthService
import ru.vsu.tripshare_mobile.services.TripService
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyLightGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint18
import ru.vsu.tripshare_mobile.ui.theme.mint36
import ru.vsu.tripshare_mobile.ui.theme.white18
import ru.vsu.tripshare_mobile.ui.theme.white24
import java.lang.Exception

@Composable
fun AddTrip(person: UserModel, navController: NavController){
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
            Text(text = "Создайте поездку!", style = mint36)
        }

        var addressFrom by remember { mutableStateOf("") }
        var places = remember { mutableStateListOf<MutableState<String>>()}
        var addressTo by remember { mutableStateOf("") }
        var departureDate by remember { mutableStateOf("") }
        var departureTime by remember { mutableStateOf("") }
        var arrivalDate by remember { mutableStateOf("") }
        var arrivalTime by remember { mutableStateOf("") }
        var participantsCount by remember { mutableStateOf("") }
        val passengersCount = remember { mutableStateOf(false) }
        var cost by remember { mutableStateOf("") }
        val smoking = remember { mutableStateOf(false) }
        val freeTrunk = remember { mutableStateOf(false) }
        val animals = remember { mutableStateOf(false) }
        var description by remember { mutableStateOf("") }

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
        ) {

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

            places.forEach{
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
                        value = it.value,
                        onValueChange = { newText ->
                            it.value = newText
                        },
                        label = { Text("Остановка") },
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { places.add(mutableStateOf("")) },
                    colors = ButtonDefaults.buttonColors(containerColor = MyMint)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Добавить остановку", style = white18)
                    }
                }
            }
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
        ) {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Укажите дату в время отправления", style = mint18)
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
                    value = departureDate,
                    onValueChange = { newText ->
                        departureDate = newText
                    },
                    label = { Text("Дата отправления") },
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
                    value = departureTime,
                    onValueChange = { newText ->
                        departureTime = newText
                    },
                    label = { Text("Время отправления") },
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
        ) {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Укажите примерную", style = mint18)
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "дату в время прибытия", style = mint18)
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
                    value = arrivalDate,
                    onValueChange = { newText ->
                        arrivalDate = newText
                    },
                    label = { Text("Дата прибытия") },
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
                    value = arrivalTime,
                    onValueChange = { newText ->
                        arrivalTime = newText
                    },
                    label = { Text("Время прибытия") },
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
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
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
                            painter = painterResource(id = R.drawable.money),
                            contentDescription = "money",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    TextField(
                        value = cost,
                        onValueChange = { newText ->
                            cost = newText
                        },
                        label = { Text("Стоимость поездки") },
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

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Выберите удобства", style = blue18)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    ru.vsu.tripshare_mobile.screens.find_trip_screens.TripFacilities(
                        text = "Максимум 2 пассажира на заднем сидении",
                        state = passengersCount
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ru.vsu.tripshare_mobile.screens.find_trip_screens.TripFacilities(
                        text = "Можно курить",
                        state = smoking
                    )

                    ru.vsu.tripshare_mobile.screens.find_trip_screens.TripFacilities(
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
                    ru.vsu.tripshare_mobile.screens.find_trip_screens.TripFacilities(
                        text = "Можно перевозить животных",
                        state = animals
                    )
                }
            }

            TextField(
                value = description,
                onValueChange = { newText ->
                    description = newText
                },
                label = { Text("Описание поездки") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    val addTripEvent = "{\"button_clicked\":\"add_trip\"}"
                    AppMetrica.reportEvent(
                        "Adding a trip event",
                        addTripEvent
                    )

                    try {
                        var stops = mutableStateListOf<StopModel>()
                        stops.add(StopModel(addressFrom, departureDate, departureTime))
                        places.forEach {
                            stops.add(StopModel(it.value, departureDate, departureTime))
                        }
                        stops.add(StopModel(addressTo, arrivalDate, arrivalTime))

                        val trip = TripModel(
                            1,
                            addressFrom,
                            addressTo,
                            stops,
                            "5 дней",
                            departureDate,
                            departureTime,
                            arrivalDate,
                            arrivalTime,
                            person,
                            listOf<UserModel>(),
                            passengersCount.value,
                            smoking.value,
                            animals.value,
                            freeTrunk.value,
                            null,
                            TripStatus.DRIVER,
                            cost.toInt()
                        )

                        CoroutineScope(Dispatchers.Main).launch {
                            TripService.addTrip(trip)
                        }
                        navController.navigate("trips_screen")
                    }catch (e: Exception){
                        Toast.makeText(
                            AppConfig.appContext,
                            "Введенные данные неккоректны. Проверьте, что дата и время удоблетворяют формату 'dd.mm.yyyy' и 'hh:mm', " +
                                    "а также что стоимость поездки и количество пассажиров - целые числа",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MyMint),
                modifier = Modifier
                    .height(70.dp)
                    .width(380.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Создать поездку", style = white24)
                }
            }
        }
    }
}



