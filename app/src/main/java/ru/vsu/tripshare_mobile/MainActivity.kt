package ru.vsu.tripshare_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.vsu.tripshare_mobile.ui.theme.mint36

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Trips()
        }
    }
}

@Composable
fun Trips() {

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ваши поездки",
            style = mint36
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth().background(Color.White),
        ) {
            itemsIndexed(
                listOf(
                    MyTripModel("Воронеж", "Москва", "Через 3 дня", "15.03.2024",
                        "15:30", "16.03.2024", "2:30",
                        "Василий", "Платон", R.drawable.vasya, 1030)
                )
            ){ _, item ->
                MyTrip(item)
            }
        }
    }
}