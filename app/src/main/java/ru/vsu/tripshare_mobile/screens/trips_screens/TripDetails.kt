package ru.vsu.tripshare_mobile.screens.trips_screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.TripParticipantModel
import ru.vsu.tripshare_mobile.ui.theme.black18
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.darkGray24
import ru.vsu.tripshare_mobile.ui.theme.darkGray36
import ru.vsu.tripshare_mobile.ui.theme.mint18
import ru.vsu.tripshare_mobile.ui.theme.mint36

@Composable
fun TripDetails(tripModel: TripModel, navController: NavController) {

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

        DriverCard(tripModel, navController = navController)
        StopsCard(tripModel, navController = navController)
        FacilitiesCard(tripModel, navController = navController)
        PassengersCard(tripModel, navController = navController)

    }
}

@Composable
fun DriverCard(tripModel: TripModel, navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
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

                Image(
                    //todo заменить !! на проверку на null
                    painter = painterResource(id = tripModel.driver.avatarId!!),
                    contentDescription = "driver",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
fun StopsCard(tripModel: TripModel, navController: NavController){
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
            tripModel.stops.forEach{
                if(it.placeName.equals(tripModel.cityFrom) ||
                    it.placeName.equals(tripModel.cityTo)) {
                    Text(text = "*" + it, style = blue18)
                }else if(it.equals(stops.get(0).placeName)
                    || it.equals(stops.get(stops.size-1).placeName)) {
                    Text(text = "*" + it, style = mint18)
                }else{
                    Text(text = "*" + it, style = darkGray18)
                }
            }
        }

        val trip = tripModel

        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
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
            modifier = Modifier.fillMaxWidth().padding(10.dp),
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
                    Image(
                        //todo заменить !! на проверку на null
                        painter = painterResource(id = it.avatarId!!),
                        contentDescription = "companion",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )

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
