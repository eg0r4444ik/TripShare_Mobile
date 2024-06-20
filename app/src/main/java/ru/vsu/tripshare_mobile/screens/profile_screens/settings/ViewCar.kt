package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.darkGray18

@Composable
fun ViewCar(user: UserModel, navController: NavController){
    val car = AppConfig.currentCar!!

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (car.imageUrl != null) {
                val painter: Painter = rememberImagePainter(car.imageUrl!!)
                Box(
                    modifier = Modifier
                        .size(300.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "Image from URL",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Text(text = "Бренд: " + car.brand, style = darkGray18)
            Text(text = "Модель: " + car.model, style = darkGray18)
            Text(text = "Цвет: " + car.color, style = darkGray18)
            Text(text = "Год производства: " + car.manufactureYear, style = darkGray18)
        }
    }
}