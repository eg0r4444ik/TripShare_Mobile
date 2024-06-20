package ru.vsu.tripshare_mobile.screens.trips_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyPurple
import ru.vsu.tripshare_mobile.ui.theme.MyRed
import ru.vsu.tripshare_mobile.ui.theme.black30
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint20
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white14

@Composable
fun TripCard(tripModel: TripModel, navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { navController.navigate("trip_details/${tripModel.id}") },
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
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TripInfoLeft(tripModel)
            TripInfoRight(tripModel)
        }
    }
}

@Composable
private fun TripInfoLeft(tripModel: TripModel){
    Column(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(0.5f)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        if(tripModel.status == TripStatus.DRIVER) {
            TripStatus(tripModel, true)
            PassengersInfo(tripModel)
        }else{
            TripStatus(tripModel, false)
            DriverInfo(tripModel)
        }

//        if(tripModel.status == TripStatus.WITHOUT_STATUS){
//            DistanceInfo(distance = tripModel.distance)
//        }else{
//            DaysUntilInfo(daysUntil = tripModel.daysUntil)
//        }
    }
}

@Composable
private fun TripInfoRight(tripModel: TripModel){
    Column (
        modifier = Modifier
            .height(300.dp)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = tripModel.cityFrom, style = mint20)
            Text(text = "-", style = mint24)
            Text(text = tripModel.cityTo, style = mint20)
        }
        Text(text = "Отправление:", style = blue18)
        Text(text = tripModel.departureDate + " " + tripModel.departureTime, style = darkGray18)
        Text(text = "Прибытие:", style = blue18)
        Text(text = tripModel.arrivalDate + " " + tripModel.arrivalTime, style = darkGray18)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 0.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(text = tripModel.cost.toString() + "₽", style = black30)
        }
    }
}

@Composable
private fun TripStatus(tripModel: TripModel, isDriver: Boolean){
    Card(shape = RoundedCornerShape(15.dp), modifier = Modifier.padding(10.dp)){
        if(tripModel.status == TripStatus.PASSENGER) {
            TripStatus(text = "Пассажир", color = MyBlue)
        }else if(tripModel.status == TripStatus.PENDING){
            TripStatus(text = "В ожидании", color = MyDarkGray)
        }else if(tripModel.status == TripStatus.REJECTED){
            TripStatus(text = "Отклонено", color = MyRed)
        }else if(tripModel.status == TripStatus.DRIVER || isDriver){
            TripStatus(text = "Водитель", color = MyPurple)
        }else if(tripModel.status == TripStatus.WITHOUT_STATUS){
            TripStatus(text = "Нет запроса", color = MyDarkGray)
        }else if(tripModel.status == TripStatus.FINISHED){
            TripStatus(text = "Завершена", color = Color.Green)
        }
    }
}

@Composable
private fun TripStatus(text: String, color: Color){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color)
            .fillMaxWidth()
            .height(34.dp)
    ) {
        Text(text = text, style = white14)
    }
}

@Composable
private fun DriverInfo(tripModel: TripModel){
    Text(text = "Водитель:", style = darkGray18)
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = tripModel.driver.surname, style = darkGray14)
        Text(text = tripModel.driver.name, style = darkGray14)
    }
}

@Composable
private fun PassengersInfo(tripModel: TripModel){
    Text(text = "Пассажиры:", style = darkGray18)
    if(tripModel.participants.size == 0){
        Text(text = "Нет пассажиров", style = darkGray14)
    }else{
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tripModel.participants.forEach { participant ->
                if(participant.avatarUrl == null) {
                    Image(
                        painterResource(id = R.drawable.baseline_person),
                        contentDescription = "participant",
                        modifier = Modifier
                            .size(if (tripModel.participants.size <= 2) 70.dp else if (tripModel.participants.size == 3) 50.dp else 40.dp)
                            .clip(CircleShape)
                    )
                }else{
                    val painter: Painter = rememberImagePainter(participant.avatarUrl!!)
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
        Text(
            text = if (tripModel.participants.size == 1) "1 пассажир" else (tripModel.participants.size.toString() + " пассажира"),
            style = darkGray14
        )
    }
}

@Composable
private fun DistanceInfo(distance: Double){
    Box(
        contentAlignment = Alignment.BottomStart
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.location),
                contentDescription = "location",
                modifier = Modifier.size(30.dp)
            )
            Text(text = " " + distance + "км", style = darkGray14)
        }
    }
}

@Composable
private fun DaysUntilInfo(daysUntil: String){
    Box(
        contentAlignment = Alignment.BottomStart
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.clock),
                contentDescription = "clock",
                modifier = Modifier.size(30.dp)
            )
            Text(text = " " + daysUntil, style = darkGray14)
        }
    }
}