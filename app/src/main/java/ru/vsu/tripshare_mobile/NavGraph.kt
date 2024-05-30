package ru.vsu.tripshare_mobile

import android.annotation.SuppressLint
import android.widget.Toast
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.screens.add_trip_screens.AddTrip
import ru.vsu.tripshare_mobile.api.RetrofitApi
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.screens.bottom_navigation.BottomNavigation
import ru.vsu.tripshare_mobile.screens.chat_screens.Chat
import ru.vsu.tripshare_mobile.screens.chat_screens.MyChats
import ru.vsu.tripshare_mobile.screens.find_trip_screens.FindTrip
import ru.vsu.tripshare_mobile.screens.find_trip_screens.FoundTripsList
import ru.vsu.tripshare_mobile.screens.profile_screens.Profile
import ru.vsu.tripshare_mobile.models.CarModel
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.MessageModel
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.ReviewModel
import ru.vsu.tripshare_mobile.models.TripParticipantModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.screens.profile_screens.UserProfile
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.AddCar
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.EditInfo
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.EditPreferences
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.Reviews
import ru.vsu.tripshare_mobile.screens.profile_screens.settings.Settings
import ru.vsu.tripshare_mobile.screens.registration_screens.FirstAuthScreen
import ru.vsu.tripshare_mobile.screens.registration_screens.RegistrationScreen
import ru.vsu.tripshare_mobile.screens.registration_screens.SecondAuthScreen
import ru.vsu.tripshare_mobile.screens.trips_screens.MyTrips
import ru.vsu.tripshare_mobile.screens.trips_screens.TripDetails
import ru.vsu.tripshare_mobile.services.AuthService
import ru.vsu.tripshare_mobile.services.CarService
import ru.vsu.tripshare_mobile.services.ChatService
import ru.vsu.tripshare_mobile.services.ReviewService
import ru.vsu.tripshare_mobile.services.UserService
import ru.vsu.tripshare_mobile.services.ValidationService
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun NavGraph(navHostController: NavHostController, startDestination: String){

    NavHost(navController = navHostController, startDestination = startDestination){
        composable("first_auth"){
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
                FindTrip(AppConfig.currentUser!!, navHostController)
            }
        }
        composable(route = "list_of_trips/{place_start}/{place_end}",
            arguments = listOf(navArgument("place_start") { type = NavType.StringType },
                navArgument("place_end") { type = NavType.StringType })
        ) { navBackStack ->
            val placeStart = navBackStack.arguments?.getString("place_start")
            val placeEnd = navBackStack.arguments?.getString("place_end")

            FoundTripsList(placeStart!!, placeEnd!!, navHostController)
        }
        composable("add_trip_screen"){
            Scaffold (
                bottomBar = {
                    BottomNavigation(navController = navHostController)
                }
            ) {
                AddTrip(AppConfig.currentUser!!, navHostController)
            }
        }
        composable("trips_screen"){
            Scaffold (
                bottomBar = {
                    BottomNavigation(navController = navHostController)
                }
            ) {
                MyTrips(navHostController)
            }
        }
        composable("chats_screen"){
            Scaffold (
                bottomBar = {
                    BottomNavigation(navController = navHostController)
                }
            ) {
                MyChats(AppConfig.currentUser!!, navHostController)
            }
        }
        composable("profile_screen"){
            Scaffold (
                bottomBar = {
                    BottomNavigation(navController = navHostController)
                }
            ) {
                var cars: List<CarModel>? = null
                val scope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    scope.launch(Dispatchers.Main) {
                        cars = ValidationService.validate(CarService.getMyCars(), "Ни одной машины не найдено")
                    }
                }

                Profile(cars, AppConfig.currentUser!!, navHostController)
            }
        }
        composable(route = "user_profile/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ){ navBackStack ->

            val userId = navBackStack.arguments?.getInt("userId")
            var user: UserModel? = null
            var cars: List<CarModel>? = null
            var isSuccessful by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                scope.launch(Dispatchers.Main) {
                    user = ValidationService.validate(UserService.getUser(userId!!), "Пользователь не найден")
                    cars = ValidationService.validate(CarService.getUsersCars(userId), "Пользователь не найден")
                    if (user != null) {
                        isSuccessful = true
                    }
                }
            }

            if(isSuccessful){
                UserProfile(cars, user!!, AppConfig.currentUser!!, navHostController)
            }

        }
//        composable(
//            route = "trip_details/{tripParticipantId}",
//            arguments = listOf(navArgument("tripParticipantId") { type = NavType.IntType }),
//            ){ navBackStack ->
//
//            val tripParticipantId = navBackStack.arguments?.getInt("tripParticipantId")
//
//            TripDetails(tripsParticipants[tripParticipantId!!], navHostController)
//        }
        composable(
            route ="reviews/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ){navBackStack ->

            val userId = navBackStack.arguments?.getInt("userId")
            var reviews: List<ReviewModel>? = null
            var isSuccessful by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                scope.launch(Dispatchers.Main) {
                    reviews = ValidationService.validate(ReviewService.getUsersReviews(userId!!), "Ни одного отзыва не найдено")
                    if (reviews != null) {
                        isSuccessful = true
                    }
                }
            }

            if(isSuccessful){
                Reviews(reviews, AppConfig.currentUser!!, navHostController)
            }
        }
        composable("edit_info"){
            EditInfo(AppConfig.currentUser!!, navHostController)
        }
        composable("settings"){
            Settings(AppConfig.currentUser!!, navHostController)
        }
        composable("edit_preferences"){
            EditPreferences(AppConfig.currentUser!!, navHostController)
        }
        composable("add_car"){
            AddCar(AppConfig.currentUser!!, navHostController)
        }
        composable(
            route ="chat/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.IntType })
        ){ navBackStack ->

            val chatId = navBackStack.arguments?.getInt("chatId")
            var chat: ChatModel? = null
            AppConfig.currentChats?.forEach { if(it.id == chatId) chat = it }
            var isSuccessful by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                scope.launch(Dispatchers.Main) {
                    val messages = ValidationService.validate(ChatService.getChatMessages(chatId!!), "Сообщения не найдены")
                    if (messages != null) {
                        chat = chat?.let {
                            ChatModel(
                                it.id,
                                it.user,
                                it.companion,
                                messages
                            )
                        }
                        isSuccessful = true
                    }
                }
            }

            if(isSuccessful){
                Chat(chat!!, AppConfig.currentUser!!, navHostController)
            }
        }
    }
}