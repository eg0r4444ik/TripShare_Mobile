package ru.vsu.tripshare_mobile.screens.add_trip_screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.CarModel
import ru.vsu.tripshare_mobile.models.StopModel
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.services.PlaceService
import ru.vsu.tripshare_mobile.services.TripService
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyLightGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint18
import ru.vsu.tripshare_mobile.ui.theme.mint36
import ru.vsu.tripshare_mobile.ui.theme.white18
import ru.vsu.tripshare_mobile.ui.theme.white24
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrip(person: UserModel, cars: List<CarModel>, navController: NavController) {
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
        var places = remember { mutableStateListOf<MutableState<String>>() }
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
                .padding(10.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight().padding(10.dp, 0.dp, 0.dp, 0.dp),
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
                        .debounce(1200)
                        .collectLatest { value ->
                            expanded = true
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
                            },
                            label = { Text("Откуда") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .menuAnchor(),
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

            places.forEach { place ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.fillMaxHeight().padding(10.dp, 0.dp, 0.dp, 0.dp),
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
                    val filteredSuggestions = suggestions.filter { it.contains(place.value, ignoreCase = true) && it != place.value }

                    LaunchedEffect(Unit) {
                        snapshotFlow { place.value }
                            .debounce(1200)
                            .collectLatest { value ->
                                expanded = true
                            }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        ExposedDropdownMenuBox(
                            expanded = expanded && filteredSuggestions.isNotEmpty(),
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                value = place.value,
                                onValueChange = {
                                    place.value = it
                                    expanded = false
                                    CoroutineScope(Dispatchers.Main).launch {
                                        val places = PlaceService.suggestPlace(place.value)
                                        if (places.isSuccess) {
                                            var addresses = mutableListOf<String>()
                                            places.getOrNull()?.forEach {
                                                addresses.add(it.address)
                                            }
                                            suggestions = addresses
                                        }
                                    }
                                },
                                label = { Text("Остановка") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .menuAnchor(),
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
                                                place.value = suggestion
                                                expanded = false
                                            }
                                        )
                                    }
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
                    modifier = Modifier.fillMaxHeight().padding(10.dp, 0.dp, 0.dp, 0.dp),
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
                        .debounce(1200)
                        .collectLatest { value ->
                            expanded = true
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
                            },
                            label = { Text("Куда") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .menuAnchor(),
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
                .padding(10.dp),
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
                Text(text = "Укажите дату", style = mint18)
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "и время отправления", style = mint18)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround
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
                        departureDate = String.format("%02d.%02d.%s", dayOfMonth, month+1, year)
                    }, year, month, day
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp)
                        .clickable { datePickerDialog.show() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar",
                        modifier = Modifier.size(50.dp)
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize().padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material3.Text(
                        text = "Дата отправления: ",
                        style = darkGray18
                    )
                    androidx.compose.material3.Text(
                        text = departureDate,
                        style = darkGray18
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val context = AppConfig.appContext
                val calendar = Calendar.getInstance()
                val hour = calendar[Calendar.HOUR_OF_DAY]
                val minute = calendar[Calendar.MINUTE]

                val timePickerDialog = TimePickerDialog(
                    context,
                    {_, hour : Int, minute: Int ->
                        departureTime = String.format("%02d:%02d", hour, minute)
                    }, hour, minute, false
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp)
                        .clickable { timePickerDialog.show() },
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
                        text = "Время отправления:",
                        style = darkGray18
                    )
                    Text(
                        text = departureTime,
                        style = darkGray18
                    )
                }
            }
        }
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
                        .clickable { datePickerDialog.show() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar",
                        modifier = Modifier.size(50.dp)
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize().padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material3.Text(
                        text = "Дата прибытия: ",
                        style = darkGray18
                    )
                    androidx.compose.material3.Text(
                        text = arrivalDate,
                        style = darkGray18
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround
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
                        .clickable { timePickerDialog.show() },
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
                        text = "Время прибытия:",
                        style = darkGray18
                    )
                    Text(
                        text = arrivalTime,
                        style = darkGray18
                    )
                }
            }
        }

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

                    var participantsCountError by remember { mutableStateOf(false) }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = participantsCount,
                            onValueChange = { newText ->
                                val number = newText.toIntOrNull()
                                if (number != null && number in 0..10) {
                                    participantsCount = newText
                                    participantsCountError = false
                                } else {
                                    if (newText.isEmpty()) {
                                        participantsCount = newText
                                    }
                                    participantsCountError = true
                                }
                            },
                            label = { Text("Количество пассажиров (0-10)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = participantsCountError,
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
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
                                text = "Введите число от 0 до 10",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(10.dp, 5.dp)
                            )
                        }
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
                            painter = painterResource(id = R.drawable.money),
                            contentDescription = "money",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    var costError by remember { mutableStateOf(false) }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = cost,
                            onValueChange = { newText ->
                                val number = newText.toIntOrNull()
                                if (number != null && number in 0..100000) {
                                    cost = newText
                                    costError = false
                                } else {
                                    if (newText.isEmpty()) {
                                        cost = newText
                                    }
                                    costError = true
                                }
                            },
                            label = { Text("Цена: (0-1000000)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = costError,
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            textStyle = darkGray18,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MyLightGray,
                                focusedIndicatorColor = MyDarkGray,
                                unfocusedIndicatorColor = MyDarkGray
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )

                        if (costError) {
                            Text(
                                text = "Введите число от 0 до 100000",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(10.dp, 5.dp)
                            )
                        }
                    }
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

        var selectedAuto by remember { mutableStateOf("Выберите авто") }
        var selectedAutoId by remember { mutableStateOf<Int?>(null) }
        var expanded by remember { mutableStateOf(false) }
        var items = mutableListOf<String>()
        val map = mutableMapOf<String, Int>()
        cars.forEach{
            val info = it.brand + " " + it.model + " " + it.manufactureYear
            items.add(info)
            map[info] = it.id
        }

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(10.dp)
                        .clickable { expanded = true },
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 5.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = selectedAuto, style = darkGray18)
                    }
                }

                DropdownMenu(
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).background(Color.White),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { item ->
                        androidx.compose.material.DropdownMenuItem(onClick = {
                            selectedAuto = item
                            selectedAutoId = map[selectedAuto]
                            expanded = false
                        }) {
                            Text(text = item)
                        }
                    }
                }
            }
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

                    if(departureDate.equals("") || departureTime.equals("")
                        || arrivalDate.equals("") || arrivalTime.equals("")
                        || addressFrom.equals("") || addressTo.equals("")
                        || participantsCount.equals("")){
                        Toast.makeText(
                            AppConfig.appContext,
                            "Заполните все поля",
                            Toast.LENGTH_LONG
                        ).show()
                    }else if(selectedAuto.equals("Выберите авто") || selectedAutoId == null){
                        Toast.makeText(
                            AppConfig.appContext,
                            "Укажите машину, на которой поедете",
                            Toast.LENGTH_LONG
                        ).show()
                    }else{
                        val inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                        val departureLocalDateTime = LocalDateTime.parse(departureDate + " " + departureTime, inputFormatter)
                        val arrivalLocalDateTime = LocalDateTime.parse(arrivalDate + " " + arrivalTime, inputFormatter)
                        if(arrivalLocalDateTime.isBefore(departureLocalDateTime)){
                            Toast.makeText(
                                AppConfig.appContext,
                                "Дата прибитыя не должна быть раньше даты отъезда",
                                Toast.LENGTH_LONG
                            ).show()
                        }else if(arrivalLocalDateTime.isBefore(LocalDateTime.now()) || departureLocalDateTime.isBefore(LocalDateTime.now())){
                            Toast.makeText(
                                AppConfig.appContext,
                                "Даты не должны быть раньше настоящего момента",
                                Toast.LENGTH_LONG
                            ).show()
                        }else {
                            CoroutineScope(Dispatchers.Main).launch {
                                try {
                                    var stops = mutableStateListOf<StopModel>()
                                    val addressFromId =
                                        PlaceService.suggestPlace(addressFrom).getOrNull()
                                            ?.get(0)?.id.toString()
                                    val addressToId = PlaceService.suggestPlace(addressTo).getOrNull()
                                        ?.get(0)?.id.toString()
                                    stops.add(StopModel(addressFromId, departureDate, departureTime))
                                    places.forEach {
                                        val id = PlaceService.suggestPlace(it.value).getOrNull()
                                            ?.get(0)?.id.toString()
                                        stops.add(StopModel(id, departureDate, departureTime))
                                    }
                                    stops.add(StopModel(addressToId, arrivalDate, arrivalTime))

                                    val trip = TripModel(
                                        0,
                                        addressFrom,
                                        addressTo,
                                        stops,
                                        "",
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
                                        selectedAutoId,
                                        TripStatus.DRIVER,
                                        cost.toInt(),
                                        participantsCount.toInt()
                                    )

                                    TripService.addTrip(trip)
                                    navController.navigate("trips_screen")
                                }catch (e: Exception){
                                    Toast.makeText(
                                        AppConfig.appContext,
                                        "Убедитесь, что вы заполнили все поля \n" +
                                                " и указали корректные места отправления и прибытия.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
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



