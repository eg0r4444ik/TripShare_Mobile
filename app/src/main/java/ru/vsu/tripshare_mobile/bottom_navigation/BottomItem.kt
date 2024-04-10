package ru.vsu.tripshare_mobile.bottom_navigation

import ru.vsu.tripshare_mobile.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    data object Screen1: BottomItem("Поиск поездки", R.drawable.find_trip, "find_trip_screen")
    data object Screen2: BottomItem("Организовать поездку", R.drawable.add_trip, "add_trip_screen")
    data object Screen3: BottomItem("Запланированные поездки", R.drawable.car, "trips_screen")
    data object Screen4: BottomItem("Чаты", R.drawable.chat, "chats_screen")
    data object Screen5: BottomItem("Профиль", R.drawable.profile, "profile_screen")

}