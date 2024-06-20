package ru.vsu.tripshare_mobile.screens.find_trip_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.screens.trips_screens.TripCard
import ru.vsu.tripshare_mobile.services.TripService
import ru.vsu.tripshare_mobile.ui.theme.mint26

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun FoundTripsList(navController: NavController){

    var trips = remember { mutableStateListOf<TripModel?>(null) }
    LaunchedEffect(Unit) {
        AppConfig.currentListOfTrips?.let {
            val response = AppConfig.currentListOfTrips
            response?.forEach {
                trips.add(TripService.tripFromDTOtoModel(it.trip).getOrNull())
            }
        }
    }

    Scaffold(
        content = {
            trips?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Найденные поездки",
                        style = mint26
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),
                    ) {
                        itemsIndexed(trips) { _, item ->
                            if(item != null && item.status == TripStatus.WITHOUT_STATUS) {
                                TripCard(item, navController = navController)
                            }
                        }
                    }
                }?: run {
                    CircularProgressIndicator()
                }
            }
        }
    )
}