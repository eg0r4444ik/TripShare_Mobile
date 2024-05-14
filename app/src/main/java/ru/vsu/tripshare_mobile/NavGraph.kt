package ru.vsu.tripshare_mobile

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.vsu.tripshare_mobile.add_trip_screens.AddTrip
import ru.vsu.tripshare_mobile.bottom_navigation.BottomNavigation
import ru.vsu.tripshare_mobile.chat_screens.Chat
import ru.vsu.tripshare_mobile.chat_screens.MyChats
import ru.vsu.tripshare_mobile.find_trip_screens.FindTrip
import ru.vsu.tripshare_mobile.find_trip_screens.FoundTripsList
import ru.vsu.tripshare_mobile.profile_screens.Profile
import ru.vsu.tripshare_mobile.models.CarModel
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.MessageModel
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.ReviewModel
import ru.vsu.tripshare_mobile.models.TripParticipantModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.profile_screens.UserProfile
import ru.vsu.tripshare_mobile.profile_screens.settings.AddCar
import ru.vsu.tripshare_mobile.profile_screens.settings.EditInfo
import ru.vsu.tripshare_mobile.profile_screens.settings.EditPreferences
import ru.vsu.tripshare_mobile.profile_screens.settings.Reviews
import ru.vsu.tripshare_mobile.profile_screens.settings.Settings
import ru.vsu.tripshare_mobile.registration_screens.FirstRegistrationScreen
import ru.vsu.tripshare_mobile.registration_screens.SecondRegistrationScreen
import ru.vsu.tripshare_mobile.trips_screens.MyTrips
import ru.vsu.tripshare_mobile.trips_screens.TripDetails
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(navHostController: NavHostController){

    val egor = UserModel(1,
        "Егор", "Рогачев", "89514960549", "egor_rogachev@yandex.ru",
        "01.01.2001", 4.8, R.drawable.egor, mutableListOf(
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
        ), mutableListOf(), "Рок, джаз", "Очень общительный",
        "Нейтральное", null, null
    )

    val user = UserModel(2,
            "Антон", "Тарарыков", "89514960549", "anthonytararykov@yandex.ru",
    "01.01.2001", 5.0, R.drawable.anton, mutableListOf(
    CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
    CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
    CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
    CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi))
    ), mutableListOf(ReviewModel(egor, 4, "Хороший водитель")), "Рок, джаз", "Очень общительный",
    "Нейтральное", null, null
    )

    val vasya = UserModel(3,
        "Василий", "Платон", "89514960549", "vasya_platon@yandex.ru",
        "01.01.2001", 4.6, R.drawable.vasya, mutableListOf(
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
        ), mutableListOf(ReviewModel(user, 5, "Хороший водитель"), ReviewModel(egor, 5, "Любит котов")), "Рок, джаз", "Очень общительный",
        "Нейтральное", null, null
    )

    val andrew = UserModel(4,
        "Андрей", "Москаленко", "89514960549", "andrew@yandex.ru",
        "01.01.2001", 5.0, R.drawable.andrew, mutableListOf(
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi))
        ), mutableListOf(ReviewModel(user, 5, "Хороший водитель"), ReviewModel(egor, 5, "Любит котов")), "Рок, джаз", "Очень общительный",
        "Нейтральное", null, null
    )

    val tima = UserModel(5,
        "Тимофей", "Улезько", "89514960549", "t.lezko@yandex.ru",
        "01.01.2001", 5.0, R.drawable.egor, mutableListOf(
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi))
        ), mutableListOf(ReviewModel(egor, 5, "Хороший водитель"), ReviewModel(andrew, 5, "Любит котов")), "Рок, джаз", "Очень общительный",
        "Нейтральное", null, null
    )

    val users = listOf(egor, user, vasya, andrew, tima)

    val car = CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi))


    val companion1 = UserModel(6, "Василий", "Платон", "89514960549", "vasya@yandex.ru",
        "01.01.2001", 5.0, R.drawable.vasya, mutableListOf(
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi))
        ), mutableListOf(ReviewModel(user, 5, "Хороший водитель"), ReviewModel(vasya, 5, "Любит котов")), "Рок, джаз", "Очень общительный",
        "Нейтральное", null, null)
    val companion2 = UserModel(7, "Егор", "Рогачев", "89514960549", "egor@yandex.ru",
        "01.01.2001", 5.0, R.drawable.egor, mutableListOf(
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi)),
            CarModel("Audi", "TT 2-nd series", "Gray", 2010, mutableListOf(R.drawable.audi))
        ), mutableListOf(ReviewModel(vasya, 5, "Хороший водитель"), ReviewModel(tima, 5, "Любит котов")), "Рок, джаз", "Очень общительный",
        "Нейтральное", null, null)

    val message1 = MessageModel(companion1, "Здравствуйте, можете меня довезти до Москвы к 18:00 14 марта?", true, Date())
    val message2 = MessageModel(user, "Привет!", true, Date())
    val message3 = MessageModel(user, "Да, конечно могу", true, Date())
    val message4 = MessageModel(companion1, "Отлично! Как вы относитесь к животным во время поездки?", true, Date())
    val message5 = MessageModel(user, "Не против животных, вы хотите с собой кого-то взять?", true, Date())
    val message6 = MessageModel(companion1, "Хочу взять собаку", true, Date())
    val message7 = MessageModel(user, "Хорошо, я напишу когда буду выезжать!", true, Date())
    val message8 = MessageModel(companion1, "Хорошо, во сколько вы примерно подъедете к месту?", true, Date())
    val message9 = MessageModel(user, "Где-то к 11:20", true, Date())
    val message10 = MessageModel(companion1, "Ок", true, Date())

    val messages = mutableListOf(message1, message2, message3, message4, message5, message6, message7, message8, message9, message10)

    val chat1 = ChatModel(1, user, companion1, messages)
    val chat2 = ChatModel(2, user, companion2, mutableListOf(MessageModel(companion2, "До встречи", false, Date())))

    val chats = listOf(chat1, chat2)

    val myTrips = listOf(
        TripModel(1,
            TripStatus.PASSENGER, "Воронеж", "Москва", listOf("г. Воронеж, ул. Кирова 12А",
            "г. Москва Певческий пер. 4"),  "Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30", vasya,
            listOf(user), car
        ),
        TripModel(2,
            TripStatus.DRIVER, "Воронеж", "Москва", listOf("г. Воронеж, ул. Кирова 12А",
                "г. Москва Певческий пер. 4"),"Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30", user,
            listOf(vasya), car
        ),
        TripModel(3,
            TripStatus.DRIVER, "Воронеж", "Москва", listOf("г. Воронеж, ул. Кирова 12А",
                "г. Москва Певческий пер. 4"),"Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30", user,
            listOf(egor, andrew, vasya), car
        ),
        TripModel(4,
            TripStatus.DRIVER, "Воронеж", "Москва", listOf("г. Воронеж, ул. Кирова 12А",
                "г. Москва Певческий пер. 4"),"Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30", user,
            listOf(egor, andrew, vasya, tima), car
        ),
        TripModel(5,
            TripStatus.REJECTED, "Воронеж", "Москва", listOf("г. Воронеж, ул. Кирова 12А",
                "г. Москва Певческий пер. 4"),"Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30", vasya,
            listOf(egor), car
        ),
        TripModel(6,
            TripStatus.PENDING, "Воронеж", "Москва", listOf("г. Воронеж, ул. Кирова 12А",
                "г. Москва Певческий пер. 4"),"Через 3 дня", "15.03.2024",
            "15:30", "16.03.2024", "2:30", andrew,
            listOf(vasya), car
        ),
    )

    val tripsParticipants = listOf(
        TripParticipantModel(user, myTrips[0], 10.0, "11 часов",
            1300, "Улица Кирова 12А",
            "Певческий пер. 4"),
        TripParticipantModel(user, myTrips[1], 10.0, "11 часов",
            1300, "Улица Кирова 12А",
            "Певческий пер. 4"),
        TripParticipantModel(user, myTrips[2], 10.0, "11 часов",
            1300, "Улица Кирова 12А",
            "Певческий пер. 4"),
        TripParticipantModel(user, myTrips[3], 10.0, "11 часов",
            1300, "Улица Кирова 12А",
            "Певческий пер. 4"),
        TripParticipantModel(user, myTrips[4], 10.0, "11 часов",
            1300, "Улица Кирова 12А",
            "Певческий пер. 4"),
        TripParticipantModel(user, myTrips[5], 10.0, "11 часов",
            1300, "Улица Кирова 12А",
            "Певческий пер. 4")

    )

    NavHost(navController = navHostController, startDestination = "welcome"){
        composable("welcome"){
            WelcomeScreen(navHostController)
        }
        composable("first_registration"){
            FirstRegistrationScreen(navHostController)
        }
        composable("second_registration"){
            SecondRegistrationScreen(navHostController)
        }
        composable("find_trip_screen"){
            Scaffold (
                bottomBar = {
                    BottomNavigation(navController = navHostController)
                }
            ) {
                FindTrip(user, navHostController)
            }
        }
        composable(route = "list_of_trips") { navBackStack ->
            FoundTripsList(myTrips, 300.0, 1300, user, navHostController)
        }
        composable("add_trip_screen"){
            Scaffold (
                bottomBar = {
                    BottomNavigation(navController = navHostController)
                }
            ) {
                AddTrip(user, navHostController)
            }
        }
        composable("trips_screen"){
            Scaffold (
                bottomBar = {
                    BottomNavigation(navController = navHostController)
                }
            ) {
                MyTrips(tripsParticipants, user, navHostController)
            }
        }
        composable("chats_screen"){
            Scaffold (
                bottomBar = {
                    BottomNavigation(navController = navHostController)
                }
            ) {
                MyChats(chats, user, navHostController)
            }
        }
        composable("profile_screen"){
            Scaffold (
                bottomBar = {
                    BottomNavigation(navController = navHostController)
                }
            ) {
                Profile(user, navHostController)
            }
        }
        composable(route = "user_profile/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ){ navBackStack ->

            val userId = navBackStack.arguments?.getInt("userId")

            UserProfile(users[userId?.minus(1)!!], user, navHostController)
        }
        composable(
            route = "trip_details/{tripId}/{userId}",
            arguments = listOf(
                navArgument("tripId") { type = NavType.IntType },
                navArgument("userId") { type = NavType.IntType })
            ){ navBackStack ->
            val tripId = navBackStack.arguments?.getInt("tripId")
            val userId = navBackStack.arguments?.getInt("userId")

            TripDetails(TripParticipantModel(users[userId?.minus(1)!!], myTrips[tripId?.minus(1)!!], 10.0, "11 часов",
                1300, "Улица Кирова 12А",
                "Певческий пер. 4"),
                user, navHostController)
        }
        composable(
            route ="reviews/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ){navBackStack ->
            val userId = navBackStack.arguments?.getInt("userId")

            Reviews(users[userId?.minus(1)!!], user, navHostController)
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
        composable(
            route ="chat/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.IntType })
        ){ navBackStack ->

            val chatId = navBackStack.arguments?.getInt("chatId")

            if(chatId == 1) {
                Chat(chat1, user, navHostController)
            }else{
                Chat(chat2, user, navHostController)
            }
        }
    }
}