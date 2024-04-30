package ru.vsu.tripshare_mobile.find_trip_screens

import androidx.compose.foundation.Image
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
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.black36
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint24

@Composable
fun FoundingTripCard(trip: TripModel, person: UserModel, navController: NavController) {
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

                Text(text = "Водитель:", style = darkGray18)
                Image(
                    //добавить проверку на null с пустой иконкой
                    painter = painterResource(id = trip.driver.imageId!!),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = trip.driver.surname, style = darkGray14)
                    Text(text = trip.driver.name, style = darkGray14)
                }

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
                        Text(text = " " + trip.distance + "км", style = darkGray14)
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
                    modifier = Modifier.fillMaxSize().padding(20.dp, 0.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Text(text = trip.cost.toString() + "₽", style = black36)
                }
            }
        }
    }
}