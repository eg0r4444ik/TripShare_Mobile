package ru.vsu.tripshare_mobile.trips_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.UserModel

@Composable
fun TripDetails(trip: TripModel, person: UserModel, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Details")
    }
}