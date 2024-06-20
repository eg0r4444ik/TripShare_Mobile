package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Screen(way: String, navController: NavController){
    navController.navigate(way)
}