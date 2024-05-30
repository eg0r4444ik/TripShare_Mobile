package ru.vsu.tripshare_mobile.screens.trips_screens

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.TripParticipantModel
import ru.vsu.tripshare_mobile.services.ChatService
import ru.vsu.tripshare_mobile.services.TripService
import ru.vsu.tripshare_mobile.ui.theme.mint36

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MyTrips(navController: NavController) {

    var trips = remember { mutableStateListOf<MutableState<TripModel>>() }
    CoroutineScope(Dispatchers.Main).launch {
        val myTrips = TripService.getTripsAsDriver()
        myTrips.onSuccess {
            myTrips.getOrNull()?.forEach {
                trips.add(mutableStateOf(it))
            }
        }.onFailure {

        }
    }

    Scaffold(
        content = {
            trips?.let {
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
                            if (item.value.status != ru.vsu.tripshare_mobile.models.TripStatus.WITHOUT_STATUS) {
                                TripCard(item.value, navController = navController)
                            }
                        }
                    }
                } ?: run {
                        CircularProgressIndicator()
                    }
            }
        }
    )
}