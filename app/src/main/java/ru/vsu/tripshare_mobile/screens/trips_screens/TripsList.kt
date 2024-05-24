package ru.vsu.tripshare_mobile.screens.trips_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.TripParticipantModel
import ru.vsu.tripshare_mobile.ui.theme.mint36

@Composable
fun MyTrips(trips: List<TripParticipantModel>, navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ваши поездки",
            style = mint36
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        ) {
            itemsIndexed(trips) { _, item ->
                if(item.status != ru.vsu.tripshare_mobile.models.TripStatus.WITHOUT_STATUS) {
                    TripCard(tripParticipant = item, navController = navController)
                }
            }
        }
    }
}