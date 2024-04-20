package ru.vsu.tripshare_mobile

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.vsu.tripshare_mobile.chat_screens.Chat
import ru.vsu.tripshare_mobile.chat_screens.MyChats
import ru.vsu.tripshare_mobile.profile_screens.Profile
import ru.vsu.tripshare_mobile.models.Car
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.MessageModel
import ru.vsu.tripshare_mobile.models.MyTripModel
import ru.vsu.tripshare_mobile.models.Review
import ru.vsu.tripshare_mobile.models.TripParticipant
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.models.User
import ru.vsu.tripshare_mobile.profile_screens.settings.AddCar
import ru.vsu.tripshare_mobile.profile_screens.settings.EditInfo
import ru.vsu.tripshare_mobile.profile_screens.settings.EditPreferences
import ru.vsu.tripshare_mobile.profile_screens.settings.Reviews
import ru.vsu.tripshare_mobile.profile_screens.settings.Settings
import ru.vsu.tripshare_mobile.trips_screens.MyTrips
import ru.vsu.tripshare_mobile.trips_screens.TripDetails
import java.util.Date

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

    val companion1 = User("Василий", "Платон", "89514960549", "vasya@yandex.ru",
        "01.01.2001", 5.0, R.drawable.vasya, listOf(
            Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi)),
            Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi)),
            Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi)),
            Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi))
        ), listOf(Review(5, "Хороший водитель"), Review(5, "Любит котов")), "Рок, джаз", "Очень общительный",
        "Нейтральное", null, null)
    val companion2 = User("Егор", "Рогачев", "89514960549", "egor@yandex.ru",
        "01.01.2001", 5.0, R.drawable.egor, listOf(
            Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi)),
            Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi)),
            Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi)),
            Car("Audi", "TT 2-nd series", "Gray", 2010, listOf(R.drawable.audi))
        ), listOf(Review(5, "Хороший водитель"), Review(5, "Любит котов")), "Рок, джаз", "Очень общительный",
        "Нейтральное", null, null)

    val chat1 = ChatModel(user, companion1, listOf(MessageModel(user, "Привет!", true, Date()),
        MessageModel(companion1, "Здравствуйте, можете меня довезти до Москвы к 18:00 14 марта?", true, Date())))
    val chat2 = ChatModel(user, companion2, listOf(MessageModel(companion2, "До встречи", false, Date())))

    val chats = listOf(chat1, chat2)

    val myTrips = listOf(
        MyTripModel(
            TripStatus.PASSENGER, "Воронеж", "Москва", "Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30",
            listOf(TripParticipant(R.drawable.vasya, "Василий", "Платон")), 1030
        ),
        MyTripModel(
            TripStatus.DRIVER, "Воронеж", "Москва", "Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30",
            listOf(TripParticipant(R.drawable.vasya, "Василий", "Платон")), 1030
        ),
        MyTripModel(
            TripStatus.DRIVER, "Воронеж", "Москва", "Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30",
            listOf(
                TripParticipant(R.drawable.egor, "Егор", "Рогачев"),
                TripParticipant(R.drawable.andrew, "Андрей", "Москаленко"),
                TripParticipant(R.drawable.vasya, "Василий", "Платон")
            ), 1030
        ),
        MyTripModel(
            TripStatus.DRIVER, "Воронеж", "Москва", "Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30",
            listOf(
                TripParticipant(R.drawable.egor, "Егор", "Рогачев"),
                TripParticipant(R.drawable.andrew, "Андрей", "Москаленко"),
                TripParticipant(R.drawable.vasya, "Василий", "Платон"),
                TripParticipant(R.drawable.egor, "Тимофей", "Улезько")
            ), 1030
        ),
        MyTripModel(
            TripStatus.REJECTED, "Воронеж", "Москва", "Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30",
            listOf(TripParticipant(R.drawable.egor, "Егор", "Рогачев")), 1030
        ),
        MyTripModel(
            TripStatus.PENDING, "Воронеж", "Москва", "Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30",
            listOf(TripParticipant(R.drawable.vasya, "Василий", "Платон")), 1030
        ),
    )

    NavHost(navController = navHostController, startDestination = "trips_screen"){
        composable("find_trip_screen"){
            MyTrips(myTrips, navHostController)
        }
        composable("add_trip_screen"){
            MyTrips(myTrips, navHostController)
        }
        composable("trips_screen"){
            MyTrips(myTrips, navHostController)
        }
        composable("chats_screen"){
            MyChats(chats, user, navHostController)
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
        composable("chat"){
            Chat(chat1, user, navHostController)
        }
    }
}