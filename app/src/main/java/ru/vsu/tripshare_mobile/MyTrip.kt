package ru.vsu.tripshare_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.black28
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint20
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white14

@Composable
fun MyTrip(trip: MyTripModel) {

    Card(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(shape = RoundedCornerShape(15.dp), modifier = Modifier.padding(10.dp)){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.background(MyBlue).fillMaxWidth(0.4f).height(34.dp)
                    ) {
                        Text(text = "Пассажир", style = white14)
                    }
                }

                Text(text = "Водитель:", style = darkGray18)
                Image(
                    painter = painterResource(id = trip.imageId),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )
                Text(text = trip.driverSurname, style = darkGray14)
                Text(text = trip.driverName, style = darkGray14)
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
            Column {
                Text(text = trip.cityFrom + "-" + trip.cityTo, style = mint24)

//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(text = trip.cityFrom, style = mint24)
//                    Text(text = "-", style = mint24)
//                    Text(text = trip.cityTo, style = mint24)
//                }
                Text(text = "Отправление:", style = blue18)
                Text(text = trip.departureDate + " " + trip.departureTime, style = darkGray18)
                Text(text = "Прибытие:", style = blue18)
                Text(text = trip.arrivalDate + " " + trip.arrivalTime, style = darkGray18)
                Box(
                    modifier = Modifier.fillMaxWidth(0.9f).fillMaxHeight(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Text(text = trip.cost.toString() + "₽", style = black28)
                }
            }
        }
    }

}