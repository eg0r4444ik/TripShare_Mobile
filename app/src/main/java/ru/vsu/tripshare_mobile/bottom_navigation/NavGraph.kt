package ru.vsu.tripshare_mobile.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.vsu.tripshare_mobile.MainActivity
import ru.vsu.tripshare_mobile.Trips

@Composable
fun NavGraph(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = "trips_screen"){
        composable("find_trip_screen"){
            Trips()
        }
        composable("add_trip_screen"){
            Trips()
        }
        composable("trips_screen"){
            Trips()
        }
        composable("chats_screen"){
            Trips()
        }
        composable("profile_screen"){
            Trips()
        }
    }
}