package ru.vsu.tripshare_mobile.screens.trips_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.api.dto.requests.RequestDTO
import ru.vsu.tripshare_mobile.api.dto.requests.RequestStatusDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.RequestModel
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.services.PlaceService
import ru.vsu.tripshare_mobile.services.RequestService
import ru.vsu.tripshare_mobile.services.TripService
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.MyPurple
import ru.vsu.tripshare_mobile.ui.theme.MyRed
import ru.vsu.tripshare_mobile.ui.theme.black18
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.darkGray24
import ru.vsu.tripshare_mobile.ui.theme.darkGray36
import ru.vsu.tripshare_mobile.ui.theme.mint18
import ru.vsu.tripshare_mobile.ui.theme.mint36
import ru.vsu.tripshare_mobile.ui.theme.white14

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun TripDetails(tripId: Int, navController: NavController) {

    var tripModel by remember { mutableStateOf<TripModel?>(null) }
    var existRequest by remember{ mutableStateOf(false) }
    var regions by remember { mutableStateOf(emptyList<String>()) }
    var requests by remember { mutableStateOf(emptyList<RequestModel>()) }

    var flag1 by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val tripModelResult = TripService.getTrip(tripId)
        if(tripModelResult.isSuccess) {
            tripModel = tripModelResult.getOrNull()
            existRequest = RequestService.existRequest(tripModel!!.id)
        }
        var list = mutableListOf<String>()
        tripModel!!.stops.forEach{
            list.add(PlaceService.getPlace(it.placeName).getOrNull()!!.address)
        }
        regions = list
        requests = RequestService.getTripRequests(tripId).getOrNull()!!
    }

    Scaffold(
        content = {
            if(tripModel != null) {
                val state = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.White)
                        .verticalScroll(state),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Детали поездки",
                        style = mint36
                    )

                    DriverCard(tripModel!!, navController = navController)
                    StopsCard(tripModel!!, regions, navController = navController)
                    FacilitiesCard(tripModel!!, navController = navController)
                    PassengersCard(tripModel!!, navController = navController)

                    if(tripModel!!.status != ru.vsu.tripshare_mobile.models.TripStatus.DRIVER){
                        if(!existRequest){
                            Button(
                                onClick = {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        var departureId: Int? = null
                                        var arrivalId: Int? = null
                                        tripModel!!.stops.forEach{
                                            if(it.placeName.equals(AppConfig.currentFindRequest!!.start.place)){
                                                departureId = it.id
                                            }
                                            if(it.placeName.equals(AppConfig.currentFindRequest!!.end.place)){
                                                arrivalId = it.id
                                            }
                                        }
                                        RequestService.addRequest(RequestDTO(AppConfig.currentFindRequest!!.needed_seats,
                                            departureId!!, arrivalId!!, tripModel!!.id))
                                        existRequest = true
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MyBlue),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(20.dp, 0.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    androidx.compose.material3.Text(text = "Оправить запрос на поездку", style = white14)
                                }
                            }
                        }
                    }else{
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                            requests.forEach {
                                if (it.status == RequestStatusDTO.CREATED) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .clickable { navController.navigate("user_profile/${it.id}") },
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
                                                .height(75.dp)
                                                .padding(10.dp, 10.dp),
                                            verticalAlignment = Alignment.Top,
                                        ) {
                                            if (it.user!!.avatarUrl == null) {
                                                Image(
                                                    painterResource(id = R.drawable.baseline_person),
                                                    contentDescription = "companion",
                                                    modifier = Modifier
                                                        .size(50.dp)
                                                        .clip(CircleShape)
                                                )
                                            } else {
                                                val painter: Painter =
                                                    rememberImagePainter(it.user.avatarUrl!!)
                                                Box(
                                                    modifier = Modifier
                                                        .size(50.dp)
                                                        .clip(CircleShape)
                                                ) {
                                                    Image(
                                                        painter = painter,
                                                        contentDescription = "Image from URL",
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .clip(CircleShape)
                                                            .border(1.dp, MyDarkGray, CircleShape)
                                                    )
                                                }
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(10.dp, 0.dp),
                                                contentAlignment = Alignment.CenterStart
                                            ) {
                                                Text(
                                                    text = it.user.surname + " " + it.user.name,
                                                    style = black18
                                                )
                                            }
                                        }

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Button(
                                                onClick = {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        RequestService.acceptRequest(it.id!!)
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
                                                    androidx.compose.material3.Text(
                                                        text = "Принять",
                                                        style = white14
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(10.dp))

                                            Button(
                                                onClick = {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        RequestService.declineRequest(it.id!!)
                                                    }
                                                },
                                                colors = ButtonDefaults.buttonColors(containerColor = MyBlue),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(50.dp)
                                                    .padding(20.dp, 0.dp)
                                            ) {
                                                Box(
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    androidx.compose.material3.Text(
                                                        text = "Отклонить",
                                                        style = white14
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if(flag1) {
                                Button(
                                    onClick = {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            RequestService.finishTrip(tripId)
                                        }
                                        flag1 = false
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MyPurple),
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .height(50.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        androidx.compose.material3.Text(
                                            text = "Завершить поездку",
                                            style = white14
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Button(
                                    onClick = {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            RequestService.errorTrip(tripId)
                                        }
                                        flag1 = false
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MyRed),
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .height(50.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        androidx.compose.material3.Text(
                                            text = "Отменить поездку",
                                            style = white14
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DriverCard(tripModel: TripModel, navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .clickable { navController.navigate("user_profile/${tripModel.driver.id}") },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Водитель", style = blue18)
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(text = tripModel.driver.name, style = darkGray36)

                if(tripModel.driver.avatarUrl == null) {
                    Image(
                        painterResource(id = R.drawable.baseline_person),
                        contentDescription = "driver",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )
                }else{
                    val painter: Painter = rememberImagePainter(tripModel.driver.avatarUrl!!)
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = "Image from URL",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .border(1.dp, MyDarkGray, CircleShape)
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun StopsCard(tripModel: TripModel, regions: List<String>, navController: NavController){
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
            modifier = Modifier.padding(10.dp, 5.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Остановки", style = darkGray24)
            }

            val stops = tripModel.stops
            regions.forEach {
                val region = ""
                if (it.equals(tripModel.cityFrom) ||
                    it.equals(tripModel.cityTo)
                ) {
                    Text(text = "*" + it, style = blue18)
                } else if (it.equals(stops.get(0).placeName)
                    || it.equals(stops.get(stops.size - 1).placeName)
                ) {
                    Text(text = "*" + it, style = mint18)
                } else {
                    Text(text = "*" + it, style = darkGray18)
                }
            }
        }

        val trip = tripModel

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Отправление:", style = blue18)
            Text(
                text = trip.departureDate + " " + trip.departureTime,
                style = darkGray18
            )
            Text(text = "Прибытие:", style = blue18)
            Text(
                text = trip.arrivalDate + " " + trip.arrivalTime,
                style = darkGray18
            )
        }
    }
}

@Composable
fun FacilitiesCard(tripModel: TripModel, navController: NavController){
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
    ){

        val trip = tripModel

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Удобства поездки", style = darkGray24)
            }

            if(trip.freeTrunk) {
                Text(text = "* Свободный багажник", style = blue18)
            }
            if(trip.maxTwoPassengersInTheBackSeat) {
                Text(text = "* Максимум два пассажира на заднем сидении", style = blue18)
            }
            if(trip.smokingAllowed) {
                Text(text = "* Разрешено курить", style = blue18)
            }
            if(trip.petsAllowed) {
                Text(text = "* Разрешено с животными", style = blue18)
            }
        }
    }
}

@Composable
fun PassengersCard(tripModel: TripModel, navController: NavController){
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
    ){

        Column() {
            tripModel.participants.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(10.dp, 10.dp)
                        .clickable { navController.navigate("user_profile/${it.id}") },
                    verticalAlignment = Alignment.Top,
                ) {
                    if(it.avatarUrl == null) {
                        Image(
                            painterResource(id = R.drawable.baseline_person),
                            contentDescription = "companion",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                    }else{
                        val painter: Painter = rememberImagePainter(it.avatarUrl!!)
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        ) {
                            Image(
                                painter = painter,
                                contentDescription = "Image from URL",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .border(1.dp, MyDarkGray, CircleShape)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp, 0.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = it.surname + " " + it.name,
                            style = black18
                        )
                    }
                }
            }
        }
    }
}
