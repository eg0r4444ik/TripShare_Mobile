package ru.vsu.tripshare_mobile.screens.trips_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.services.TripService
import ru.vsu.tripshare_mobile.ui.theme.mint36

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyTrips(navController: NavController) {

    var trips by remember { mutableStateOf(emptyList<TripModel>()) }

    LaunchedEffect(Unit) {
        val tripsResult = TripService.getTripsAsDriver()
        if(tripsResult.isSuccess) {
            trips = tripsResult.getOrNull()!!
        }
    }

    Scaffold(
        content = {
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
//                        if (item.status != ru.vsu.tripshare_mobile.models.TripStatus.WITHOUT_STATUS) {
                            TripCard(item, navController = navController)
//                        }
                    }
                }
            }
        }
    )

}