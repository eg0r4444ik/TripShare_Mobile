package ru.vsu.tripshare_mobile.trips_screens

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
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.TripParticipantModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.black18
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.darkGray24
import ru.vsu.tripshare_mobile.ui.theme.darkGray36
import ru.vsu.tripshare_mobile.ui.theme.darkGray48
import ru.vsu.tripshare_mobile.ui.theme.mint18
import ru.vsu.tripshare_mobile.ui.theme.mint36

@Composable
fun TripDetails(tripParticipant: TripParticipantModel, person: UserModel, navController: NavController) {

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

        DriverCard(tripParticipant = tripParticipant, navController = navController)
        StopsCard(tripParticipant = tripParticipant, navController = navController)
        FacilitiesCard(tripParticipant = tripParticipant, navController = navController)
        PassengersCard(tripParticipant = tripParticipant, navController = navController)

    }
}

@Composable
fun DriverCard(tripParticipant: TripParticipantModel, navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
            .clickable { navController.navigate("user_profile/${tripParticipant.trip.driver.id}") },
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

                Text(text = tripParticipant.trip.driver.name, style = darkGray36)

                Image(
                    //todo заменить !! на проверку на null
                    painter = painterResource(id = tripParticipant.trip.driver.imageId!!),
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
fun StopsCard(tripParticipant: TripParticipantModel, navController: NavController){
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

            val addresses = tripParticipant.trip.addresses
            tripParticipant.trip.addresses.forEach{
                if(it.equals(tripParticipant.addressFrom) ||
                    it.equals(tripParticipant.addressTo)) {
                    Text(text = "*" + it, style = blue18)
                }else if(it.equals(addresses.get(0))
                    || it.equals(addresses.get(addresses.size-1))) {
                    Text(text = "*" + it, style = mint18)
                }else{
                    Text(text = "*" + it, style = darkGray18)
                }
            }
        }

        val trip = tripParticipant.trip

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
fun FacilitiesCard(tripParticipant: TripParticipantModel, navController: NavController){
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

        val trip = tripParticipant.trip

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

            trip.facilities.forEach{
                Text(text = "*" + it, style = blue18)
            }
        }
    }
}

@Composable
fun PassengersCard(tripParticipant: TripParticipantModel, navController: NavController){
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
            tripParticipant.trip.participants.forEach {
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
                        painter = painterResource(id = it.imageId!!),
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
