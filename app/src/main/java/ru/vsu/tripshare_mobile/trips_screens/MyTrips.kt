package ru.vsu.tripshare_mobile.trips_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.models.MyTripModel
import ru.vsu.tripshare_mobile.models.TripParticipant
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.ui.theme.mint36

@Composable
fun MyTrips(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ваши поездки",
            style = mint36
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        ) {
            itemsIndexed(
                listOf(
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
            ) { _, item ->
                if(item.status == TripStatus.DRIVER){
                    MyTripAsDriver(item, navController)
                }else {
                    MyTripAsPassenger(item, navController)
                }
            }
        }
    }
}