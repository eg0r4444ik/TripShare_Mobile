package ru.vsu.tripshare_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import ru.vsu.tripshare_mobile.bottom_navigation.BottomNavigation
import ru.vsu.tripshare_mobile.bottom_navigation.NavGraph
import ru.vsu.tripshare_mobile.bottom_panel.SearchTripBottomPanel
import ru.vsu.tripshare_mobile.ui.theme.mint36

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold (
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ){
        NavGraph(navHostController = navController)
    }
}

@Preview
@Composable
fun Trips() {

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    MyTripAsPassengerModel("Воронеж", "Москва", "Через 3 дня", "15.03.2024",
                        "15:30", "16.03.2024", "2:30",
                        "Василий", "Платон", R.drawable.vasya, 1030),
                    MyTripAsPassengerModel("Воронеж", "Москва", "Через 3 дня", "15.03.2024",
                        "15:30", "16.03.2024", "2:30",
                        "Василий", "Платон", R.drawable.vasya, 1030),
                    MyTripAsPassengerModel("Воронеж", "Москва", "Через 3 дня", "15.03.2024",
                        "15:30", "16.03.2024", "2:30",
                        "Василий", "Платон", R.drawable.vasya, 1030),
                    MyTripAsPassengerModel("Воронеж", "Москва", "Через 3 дня", "15.03.2024",
                        "15:30", "16.03.2024", "2:30",
                        "Василий", "Платон", R.drawable.vasya, 1030),
                    MyTripAsPassengerModel("Воронеж", "Москва", "Через 3 дня", "15.03.2024",
                        "15:30", "16.03.2024", "2:30",
                        "Василий", "Платон", R.drawable.vasya, 1030)
                )
            ){ _, item ->
                MyTripAsPassenger(item)
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            SearchTripBottomPanel()
        }
    }
}