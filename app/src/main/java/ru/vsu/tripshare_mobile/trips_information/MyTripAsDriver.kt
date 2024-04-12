package ru.vsu.tripshare_mobile.trips_information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import ru.vsu.tripshare_mobile.trips_information.models.MyTripModel
import ru.vsu.tripshare_mobile.ui.theme.MyPurple
import ru.vsu.tripshare_mobile.ui.theme.black28
import ru.vsu.tripshare_mobile.ui.theme.black36
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white14

@Composable
fun MyTripAsDriver(trip: MyTripModel, navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { navController.navigate("trip_details") },
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
            Column(
                modifier = Modifier.height(300.dp).fillMaxWidth(0.5f).padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Card(shape = RoundedCornerShape(15.dp), modifier = Modifier.padding(10.dp)){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(MyPurple)
                            .fillMaxWidth()
                            .height(34.dp)
                    ) {
                        Text(text = "Водитель", style = white14)
                    }
                }

                Text(text = "Пассажиры:", style = darkGray18)
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    trip.participants.forEach{participant ->
                        Image(
                            painter = painterResource(id = participant.imageId),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(if(trip.participants.size <= 2) 70.dp else if(trip.participants.size == 3) 50.dp else 40.dp)
                                .clip(CircleShape)
                        )
                    }
                }
                Text(text = if(trip.participants.size == 1) "1 пассажир" else (trip.participants.size.toString() + " пассажира"),
                    style = darkGray14)

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
                            contentDescription = "image",
                            modifier = Modifier.size(30.dp)
                        )
                        Text(text = " " + trip.daysUntil, style = darkGray14)
                    }
                }
            }
            Column (
                modifier = Modifier.height(300.dp).padding(10.dp)
            ){
//                Text(text = trip.cityFrom + "-" + trip.cityTo, style = mint24)

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = trip.cityFrom, style = mint24)
                    Text(text = "-", style = mint24)
                    Text(text = trip.cityTo, style = mint24)
                }
                Text(text = "Отправление:", style = blue18)
                Text(text = trip.departureDate + " " + trip.departureTime, style = darkGray18)
                Text(text = "Прибытие:", style = blue18)
                Text(text = trip.arrivalDate + " " + trip.arrivalTime, style = darkGray18)

                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(20.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Text(text = trip.cost.toString() + "₽", style = black36)
                }
            }
        }
    }

}