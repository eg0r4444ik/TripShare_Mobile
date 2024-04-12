package ru.vsu.tripshare_mobile.bottom_navigation

import ru.vsu.tripshare_mobile.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    data object FindTripScreen: BottomItem("Найти", R.drawable.find_trip, "find_trip_screen")
    data object AddTripScreen: BottomItem("Создать", R.drawable.add_trip, "add_trip_screen")
    data object TripsScreen: BottomItem("Поездки", R.drawable.car, "trips_screen")
    data object ChatsScreen: BottomItem("Чаты", R.drawable.chat, "chats_screen")
    data object ProfileScreen: BottomItem("Профиль", R.drawable.profile, "profile_screen")

}