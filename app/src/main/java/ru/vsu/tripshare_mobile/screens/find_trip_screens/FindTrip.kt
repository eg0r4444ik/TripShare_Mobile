package ru.vsu.tripshare_mobile.screens.find_trip_screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.api.dto.trips.ComfortsDTO
import ru.vsu.tripshare_mobile.api.dto.trips.FindTripRequestDTO
import ru.vsu.tripshare_mobile.api.dto.trips.StopDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.services.PlaceService
import ru.vsu.tripshare_mobile.services.TripService
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyLightGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint36
import ru.vsu.tripshare_mobile.ui.theme.white18
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindTrip(navController: NavController){

    val state = rememberScrollState()

    var flag1 by remember { mutableStateOf(false) }
    var flag2 by remember { mutableStateOf(false) }

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

                var addressFrom by remember { mutableStateOf("") }
                var addressTo by remember { mutableStateOf("") }
                var arrivalDate by remember { mutableStateOf("") }
                var arrivalTime by remember { mutableStateOf("") }
                var participantsCount by remember { mutableStateOf("") }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp, 0.dp, 0.dp, 0.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "location",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    var expanded by remember { mutableStateOf(false) }
                    var suggestions by remember { mutableStateOf(emptyList<String>()) }
                    val filteredSuggestions = suggestions.filter { it.contains(addressFrom, ignoreCase = true) && it != addressFrom }

                    LaunchedEffect(Unit) {
                        snapshotFlow { addressFrom }
                            .debounce(1500)
                            .collectLatest { value ->
                                if(flag1) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        val places = PlaceService.suggestPlace(addressFrom)
                                        if (places.isSuccess) {
                                            var addresses = mutableListOf<String>()
                                            places.getOrNull()?.forEach {
                                                addresses.add(it.address)
                                            }
                                            suggestions = addresses
                                        }
                                    }
                                    expanded = true
                                }
                            }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        ExposedDropdownMenuBox(
                            expanded = expanded && filteredSuggestions.isNotEmpty(),
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                value = addressFrom,
                                onValueChange = {
                                    addressFrom = it
                                    expanded = false
                                    flag1 = true
                                    flag2 = false
                                },
                                label = { Text("Откуда") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .menuAnchor()
                                    .clickable { flag1 = true
                                        flag2 = false },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MyLightGray,
                                    focusedIndicatorColor = MyDarkGray,
                                    unfocusedIndicatorColor = MyDarkGray
                                ),
                                shape = RoundedCornerShape(15.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text
                                )
                            )

                            if(suggestions.isNotEmpty()) {
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    filteredSuggestions.forEach { suggestion ->
                                        DropdownMenuItem(
                                            text = { Text(text = suggestion) },
                                            onClick = {
                                                addressFrom = suggestion
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp, 0.dp, 0.dp, 0.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "location",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    var expanded by remember { mutableStateOf(false) }
                    var suggestions by remember { mutableStateOf(emptyList<String>()) }
                    val filteredSuggestions = suggestions.filter { it.contains(addressTo, ignoreCase = true) && it != addressTo }

                    LaunchedEffect(Unit) {
                        snapshotFlow { addressTo }
                            .debounce(1500)
                            .collectLatest { value ->
                                if(flag2) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        val places = PlaceService.suggestPlace(addressTo)
                                        if (places.isSuccess) {
                                            var addresses = mutableListOf<String>()
                                            places.getOrNull()?.forEach {
                                                addresses.add(it.address)
                                            }
                                            suggestions = addresses
                                        }
                                    }
                                    expanded = true
                                }
                            }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        ExposedDropdownMenuBox(
                            expanded = expanded && filteredSuggestions.isNotEmpty(),
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                value = addressTo,
                                onValueChange = {
                                    addressTo = it
                                    expanded = false
                                    flag1 = false
                                    flag2 = true
                                },
                                label = { Text("Куда") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .menuAnchor()
                                    .clickable { flag1 = false
                                        flag2 = true },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MyLightGray,
                                    focusedIndicatorColor = MyDarkGray,
                                    unfocusedIndicatorColor = MyDarkGray
                                ),
                                shape = RoundedCornerShape(15.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text
                                )
                            )

                            if(suggestions.isNotEmpty()) {
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    filteredSuggestions.forEach { suggestion ->
                                        DropdownMenuItem(
                                            text = { Text(text = suggestion) },
                                            onClick = {
                                                addressTo = suggestion
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val context = AppConfig.appContext

                    val year: Int
                    val month: Int
                    val day: Int

                    val calendar = Calendar.getInstance()
                    year = calendar.get(Calendar.YEAR)
                    month = calendar.get(Calendar.MONTH)
                    day = calendar.get(Calendar.DAY_OF_MONTH)
                    calendar.time = Date()

                    val datePickerDialog = DatePickerDialog(
                        context,
                        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                            arrivalDate = String.format("%02d.%02d.%s", dayOfMonth, month+1, year)
                        }, year, month, day
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp)
                            .clickable {
                                flag1 = false
                                flag2 = false
                                datePickerDialog.show()
                                       },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = "calendar",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        androidx.compose.material3.Text(
                            text = "Дата: ",
                            style = darkGray18
                        )
                        androidx.compose.material3.Text(
                            text = arrivalDate,
                            style = darkGray18
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val context = AppConfig.appContext
                    val calendar = Calendar.getInstance()
                    val hour = calendar[Calendar.HOUR_OF_DAY]
                    val minute = calendar[Calendar.MINUTE]

                    val timePickerDialog = TimePickerDialog(
                        context,
                        {_, hour : Int, minute: Int ->
                            arrivalTime = String.format("%02d:%02d", hour, minute)
                        }, hour, minute, false
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp)
                            .clickable {
                                flag1 = false
                                flag2 = false
                                timePickerDialog.show()
                                       },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.clock),
                            contentDescription = "clock",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Время:",
                            style = darkGray18
                        )
                        Text(
                            text = arrivalTime,
                            style = darkGray18
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
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
                    var participantsCountError by remember { mutableStateOf(false) }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = participantsCount,
                            onValueChange = { newText ->
                                flag1 = false
                                flag2 = false
                                val number = newText.toIntOrNull()
                                if (number != null && number in 1..10) {
                                    participantsCount = newText
                                    participantsCountError = false
                                } else {
                                    if (newText.isEmpty()) {
                                        participantsCount = newText
                                    }
                                    participantsCountError = true
                                }
                            },
                            label = { Text("Количество пассажиров (1-10)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = participantsCountError,
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

                        if (participantsCountError) {
                            Text(
                                text = "Введите число от 1 до 10",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(10.dp, 5.dp)
                            )
                        }
                    }
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
                        onClick = {
                            val findTripEvent = "{\"button_clicked\":\"find_trip\"}"
                            AppMetrica.reportEvent(
                                "Finding a trip event",
                                findTripEvent
                            )

                            if(arrivalDate.equals("") || arrivalTime.equals("")
                                || addressFrom.equals("") || addressTo.equals("")
                                || participantsCount.equals("")){
                                Toast.makeText(
                                    AppConfig.appContext,
                                    "Заполните все поля",
                                    Toast.LENGTH_LONG
                                ).show()
                            }else {
                                val inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                                val localDateTime = LocalDateTime.parse(arrivalDate + " " + arrivalTime, inputFormatter)
                                if(localDateTime.isBefore(LocalDateTime.now())){
                                    Toast.makeText(
                                        AppConfig.appContext,
                                        "Дата не должны быть раньше настоящего момента",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }else {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        val start =
                                            PlaceService.suggestPlace(addressFrom).getOrNull()
                                                ?.get(0)?.id.toString()
                                        val end = PlaceService.suggestPlace(addressTo).getOrNull()
                                            ?.get(0)?.id.toString()
                                        val inputFormatter =
                                            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                                        val localDateTime = LocalDateTime.parse(
                                            arrivalDate + " " + arrivalTime,
                                            inputFormatter
                                        )
                                        val instant = localDateTime.toInstant(ZoneOffset.UTC)
                                        val isoFormatter = DateTimeFormatter.ISO_INSTANT
                                        val iso8601String = isoFormatter.format(instant)
                                        val request = FindTripRequestDTO(
                                            StopDTO(start, start),
                                            StopDTO(end, end),
                                            ComfortsDTO(
                                                passengersCount.value,
                                                smoking.value,
                                                animals.value,
                                                freeTrunk.value
                                            ),
                                            iso8601String,
                                            participantsCount.toInt()
                                        )
                                        AppConfig.currentFindRequest = request
                                        AppConfig.currentListOfTrips =
                                            TripService.findTrips(request).getOrNull()
                                        navController.navigate("list_of_trips")
                                    }
                                }
                            }
                                  },
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
