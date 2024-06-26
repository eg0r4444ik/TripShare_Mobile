package ru.vsu.tripshare_mobile

import android.annotation.SuppressLint
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.CarModel
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.screens.add_trip_screens.AddTrip
import ru.vsu.tripshare_mobile.screens.bottom_navigation.BottomNavigation
import ru.vsu.tripshare_mobile.screens.chat_screens.Chat
import ru.vsu.tripshare_mobile.screens.chat_screens.MyChats
import ru.vsu.tripshare_mobile.screens.find_trip_screens.FindTrip
import ru.vsu.tripshare_mobile.screens.find_trip_screens.FoundTripsList
import ru.vsu.tripshare_mobile.screens.payments_screens.AddPayment
import ru.vsu.tripshare_mobile.screens.payments_screens.Payments
import ru.vsu.tripshare_mobile.screens.profile_screens.Profile
import ru.vsu.tripshare_mobile.screens.profile_screens.UserProfile
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.AddCar
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.AddReview
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.EditInfo
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.EditPreferences
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.Reviews
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.Screen
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.Settings
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.ViewCar
import ru.vsu.tripshare_mobile.screens.registration_screens.AuthorizationRequestScreen
import ru.vsu.tripshare_mobile.screens.registration_screens.FirstAuthScreen
import ru.vsu.tripshare_mobile.screens.registration_screens.RegistrationScreen
import ru.vsu.tripshare_mobile.screens.registration_screens.SecondAuthScreen
import ru.vsu.tripshare_mobile.screens.trips_screens.MyTrips
import ru.vsu.tripshare_mobile.screens.trips_screens.TripDetails
import ru.vsu.tripshare_mobile.services.CarService
import ru.vsu.tripshare_mobile.services.UserService

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun NavGraph(navHostController: NavHostController, startDestination: String){

    NavHost(navController = navHostController, startDestination = startDestination){
        composable("first_auth"){
            AppConfig.currentUser = null
            AppConfig.authManager.clearToken()
            FirstAuthScreen(navHostController)
        }
        composable("second_auth/{phone}",
                arguments = listOf(navArgument("phone") { type = NavType.StringType })
        ){navBackStack ->
            val phone = navBackStack.arguments?.getString("phone")
            SecondAuthScreen(phone!!, navHostController)
        }
        composable(route = "registration/{phone}",
            arguments = listOf(navArgument("phone") { type = NavType.StringType })
        ){ navBackStack ->
            val phone = navBackStack.arguments?.getString("phone")
            RegistrationScreen(phone!!, navHostController)
        }
        composable("find_trip_screen"){
            Scaffold (
                bottomBar = {
                    BottomNavigation(navController = navHostController)
                }
            ) {
                FindTrip(navHostController)
            }
        }
        composable(route = "list_of_trips") {
            FoundTripsList(navHostController)
        }
        composable("add_trip_screen"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                val scope = rememberCoroutineScope()
                var cars by remember { mutableStateOf(listOf<CarModel>()) }
                var isLoading by remember { mutableStateOf(true) }

                LaunchedEffect(AppConfig.currentUser!!.id) {
                    scope.launch(Dispatchers.Main) {
                        val myCarsResult = CarService.getUsersCars(AppConfig.currentUser!!.id)
                        myCarsResult.onSuccess { carList ->
                            cars = carList ?: listOf()
                            isLoading = false
                        }.onFailure {
                            isLoading = false
                        }
                    }
                }

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Scaffold(
                        bottomBar = {
                            BottomNavigation(navController = navHostController)
                        }
                    ) {
                        AddTrip(AppConfig.currentUser!!, cars!!, navHostController)
                    }
                }
            }
        }
        composable("trips_screen"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                Scaffold(
                    bottomBar = {
                        BottomNavigation(navController = navHostController)
                    }
                ) {
                    MyTrips(navHostController)
                }
            }
        }
        composable("chats_screen"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                Scaffold(
                    bottomBar = {
                        BottomNavigation(navController = navHostController)
                    }
                ) {
                    MyChats(AppConfig.currentUser!!, navHostController)
                }
            }
        }
        composable("profile_screen"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                Scaffold(
                    bottomBar = {
                        BottomNavigation(navController = navHostController)
                    }
                ) {
                    Profile(AppConfig.currentUser!!, navHostController)
                }
            }
        }
        composable(route = "user_profile/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ){ navBackStack ->
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                val userId = navBackStack.arguments?.getInt("userId")

                UserProfile(userId!!, AppConfig.currentUser!!, navHostController)
            }

        }
        composable(
            route = "trip_details/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.IntType }),
            ){ navBackStack ->
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                val tripId = navBackStack.arguments?.getInt("tripId")

                TripDetails(tripId!!, navHostController)
            }
        }
        composable(
            route ="reviews/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ){navBackStack ->
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                val userId = navBackStack.arguments?.getInt("userId")

                Reviews(userId!!, navHostController)
            }
        }
        composable(
            route ="add_review/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ){navBackStack ->
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                val userId = navBackStack.arguments?.getInt("userId")

                AddReview(userId!!, navHostController)
            }
        }
        composable("edit_info"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                EditInfo(AppConfig.currentUser!!, navHostController)
            }
        }
        composable("settings"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                Settings(AppConfig.currentUser!!, navHostController)
            }
        }
        composable("add_payment"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                AddPayment(navHostController)
            }
        }
        composable("payments"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                Payments(navHostController)
            }
        }
        composable("edit_preferences"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                EditPreferences(AppConfig.currentUser!!, navHostController)
            }
        }
        composable("add_car"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                AddCar(AppConfig.currentUser!!, navHostController)
            }
        }
        composable("view_car"){
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                ViewCar(AppConfig.currentUser!!, navHostController)
            }
        }
        composable(route ="screen/{way}",
            arguments = listOf(navArgument("way") { type = NavType.StringType })){navBackStack ->
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                val way = navBackStack.arguments?.getString("way")
                Screen(way!!, navHostController)
            }
        }
        composable(
            route ="chat/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.IntType })
        ){ navBackStack ->
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                val chatId = navBackStack.arguments?.getInt("chatId")
                var chat: ChatModel? = null

                AppConfig.currentChats?.forEach {
                    if (it.id == chatId) {
                        chat = it
                    }
                }

                Chat(chat!!, AppConfig.currentUser!!, navHostController)
            }
        }
        composable(
            route ="new_chat/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ){ navBackStack ->
            if(AppConfig.currentUser == null){
                AuthorizationRequestScreen(navHostController)
            }else {
                val userId = navBackStack.arguments?.getInt("userId")
                var chat: ChatModel? = null

                AppConfig.currentChats?.forEach {
                    if ((it.companion.id == userId && it.user.id == AppConfig.currentUser!!.id)
                        || (it.user.id == userId && it.companion.id == AppConfig.currentUser!!.id)
                    ) {
                        chat = it
                    }
                }

                if (chat != null) {
                    Chat(chat!!, AppConfig.currentUser!!, navHostController)
                } else {
                    var companion by remember { mutableStateOf<UserModel?>(null) }

                    LaunchedEffect(Unit) {
                        val userResult = UserService.getUser(userId!!)
                        if (userResult.isSuccess) {
                            companion = userResult.getOrNull()!!
                        }
                    }

                    Scaffold(
                        content = {
                            if (companion != null) {
                                val newChat =
                                    ChatModel(
                                        0,
                                        AppConfig.currentUser!!,
                                        companion!!,
                                        mutableListOf()
                                    )
                                Chat(newChat, AppConfig.currentUser!!, navHostController)
                            }
                        }
                    )
                }
            }
        }
    }
}