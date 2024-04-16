package ru.vsu.tripshare_mobile

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.vsu.tripshare_mobile.profile_screens.Profile
import ru.vsu.tripshare_mobile.profile_screens.models.Car
import ru.vsu.tripshare_mobile.profile_screens.models.Review
import ru.vsu.tripshare_mobile.profile_screens.models.User
import ru.vsu.tripshare_mobile.profile_screens.settings.AddCar
import ru.vsu.tripshare_mobile.profile_screens.settings.EditInfo
import ru.vsu.tripshare_mobile.profile_screens.settings.EditPreferences
import ru.vsu.tripshare_mobile.profile_screens.settings.Reviews
import ru.vsu.tripshare_mobile.profile_screens.settings.Settings
import ru.vsu.tripshare_mobile.trips_screens.MyTrips
import ru.vsu.tripshare_mobile.trips_screens.TripDetails

@Composable
fun NavGraph(navHostController: NavHostController){

    val user = User(
            "Антон", "Тарарыков", "89514960549", "anthonytararykov@yandex.ru",
    "01.01.2001", 5.0, R.drawable.anton, listOf(
    Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi)),
    Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi)),
    Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi)),
    Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi))
    ), listOf(Review(5, "Хороший водитель"), Review(5, "Любит котов")), "Рок, джаз", "Очень общительный",
    "Нейтральное", null, null
    )

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
            Profile(user, navHostController)
        }
        composable("trip_details"){
            TripDetails()
        }
        composable("reviews"){
            Reviews(user, navHostController)
        }
        composable("edit_info"){
            EditInfo(user, navHostController)
        }
        composable("settings"){
            Settings(user, navHostController)
        }
        composable("edit_preferences"){
            EditPreferences(user, navHostController)
        }
        composable("add_car"){
            AddCar(user, navHostController)
        }
    }
}