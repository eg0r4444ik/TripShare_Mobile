package ru.vsu.tripshare_mobile

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.vsu.tripshare_mobile.trips_information.MyTrips
import ru.vsu.tripshare_mobile.trips_information.TripDetails

@Composable
fun NavGraph(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = "trips_screen"){
        composable("find_trip_screen"){
            MyTrips(navHostController)
        }
        composable("add_trip_screen"){
            MyTrips(navHostController)
        }
        composable("trips_screen"){
            MyTrips(navHostController)
        }
        composable("chats_screen"){
            MyTrips(navHostController)
        }
        composable("profile_screen"){
            MyTrips(navHostController)
        }
        composable("trip_details"){
            TripDetails()
        }
    }
}