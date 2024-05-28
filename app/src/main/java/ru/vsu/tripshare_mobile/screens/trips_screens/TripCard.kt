package ru.vsu.tripshare_mobile.screens.trips_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.models.TripParticipantModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyPurple
import ru.vsu.tripshare_mobile.ui.theme.MyRed
import ru.vsu.tripshare_mobile.ui.theme.black36
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white14

@Composable
fun TripCard(tripParticipant: TripParticipantModel, navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { navController.navigate("trip_details/${tripParticipant.id}") },
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
            TripInfoLeft(tripParticipant = tripParticipant)
            TripInfoRight(tripParticipant = tripParticipant)
        }
    }
}

@Composable
private fun TripInfoLeft(tripParticipant: TripParticipantModel){
    Column(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(0.5f)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        TripStatus(tripParticipant = tripParticipant)

        if(tripParticipant.status == TripStatus.DRIVER) {
            PassengersInfo(tripParticipant = tripParticipant)
        }else{
            DriverInfo(tripParticipant = tripParticipant)
        }

        if(tripParticipant.status == TripStatus.WITHOUT_STATUS){
            DistanceInfo(distance = tripParticipant.distance)
        }else{
            DaysUntilInfo(daysUntil = tripParticipant.trip.daysUntil)
        }
    }
}

@Composable
private fun TripInfoRight(tripParticipant: TripParticipantModel){
    Column (
        modifier = Modifier
            .height(300.dp)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = tripParticipant.trip.cityFrom, style = mint24)
            Text(text = "-", style = mint24)
            Text(text = tripParticipant.trip.cityTo, style = mint24)
        }
        Text(text = "Отправление:", style = blue18)
        Text(text = tripParticipant.trip.departureDate + " " + tripParticipant.trip.departureTime, style = darkGray18)
        Text(text = "Прибытие:", style = blue18)
        Text(text = tripParticipant.trip.arrivalDate + " " + tripParticipant.trip.arrivalTime, style = darkGray18)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 0.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(text = tripParticipant.cost.toString() + "₽", style = black36)
        }
    }
}

@Composable
private fun TripStatus(tripParticipant: TripParticipantModel){
    Card(shape = RoundedCornerShape(15.dp), modifier = Modifier.padding(10.dp)){
        if(tripParticipant.status == TripStatus.PASSENGER) {
            TripStatus(text = "Пассажир", color = MyBlue)
        }else if(tripParticipant.status == TripStatus.PENDING){
            TripStatus(text = "В ожидании", color = MyDarkGray)
        }else if(tripParticipant.status == TripStatus.REJECTED){
            TripStatus(text = "Отклонено", color = MyRed)
        }else if(tripParticipant.status == TripStatus.DRIVER){
            TripStatus(text = "Водитель", color = MyPurple)
        }else if(tripParticipant.status == TripStatus.WITHOUT_STATUS){
            TripStatus(text = "Нет запроса", color = MyDarkGray)
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
private fun DriverInfo(tripParticipant: TripParticipantModel){
    Text(text = "Водитель:", style = darkGray18)
    Image(
        //todo добавить проверку на null с пустой иконкой
        painter = painterResource(id = tripParticipant.trip.driver.avatarId!!),
        contentDescription = "image",
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = tripParticipant.trip.driver.surname, style = darkGray14)
        Text(text = tripParticipant.trip.driver.name, style = darkGray14)
    }
}

@Composable
private fun PassengersInfo(tripParticipant: TripParticipantModel){
    Text(text = "Пассажиры:", style = darkGray18)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tripParticipant.trip.participants.forEach { participant ->
            Image(
                //todo добавить проверку на null с пустой иконкой
                painter = painterResource(id = participant.avatarId!!),
                contentDescription = "image",
                modifier = Modifier
                    .size(if (tripParticipant.trip.participants.size <= 2) 70.dp else if (tripParticipant.trip.participants.size == 3) 50.dp else 40.dp)
                    .clip(CircleShape)
            )
        }
    }
    Text(
        text = if (tripParticipant.trip.participants.size == 1) "1 пассажир" else (tripParticipant.trip.participants.size.toString() + " пассажира"),
        style = darkGray14
    )
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