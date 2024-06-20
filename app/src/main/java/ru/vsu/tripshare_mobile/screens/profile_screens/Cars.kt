package ru.vsu.tripshare_mobile.screens.profile_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.models.CarModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.darkGray24
import ru.vsu.tripshare_mobile.ui.theme.darkGray36
import ru.vsu.tripshare_mobile.ui.theme.white18

@Composable
fun Cars(cars: List<CarModel>?, user: UserModel, person: UserModel, navController: NavController){
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
        ),
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceAround
        ) {
            if(user.id == person.id) {
                Text(text = "Ваши авто", style = darkGray36, modifier = Modifier.padding(10.dp))
            }else{
                Text(text = "Авто", style = darkGray36, modifier = Modifier.padding(10.dp))
            }

            if(cars != null) {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    itemsIndexed(cars) { _, item ->
                        Column(
                            modifier = Modifier
                                .padding(10.dp, 10.dp)
                                .clickable { navController.navigate("add_car") },
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if(item.imageUrl == null){
                                Image(
                                    painterResource(id = R.drawable.baseline_car),
                                    contentDescription = "car",
                                    modifier = Modifier.size(160.dp, 120.dp)
                                )
                            }else{
                                val painter: Painter = rememberImagePainter(item.imageUrl!!)
                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                ) {
                                    Image(
                                        painter = painter,
                                        contentDescription = "Image from URL",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .border(1.dp, MyDarkGray, CircleShape)
                                    )
                                }
                            }
                            Text(text = item.model + " " + item.brand, style = darkGray14)
                        }
                    }
                }
            }else{
                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "У вас нет машин", style = darkGray24)
                }
            }

            if(person.id == user.id) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("add_car") },
                        colors = ButtonDefaults.buttonColors(containerColor = MyMint),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(20.dp, 0.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Добавить автомобиль ", style = white18)
                        }
                    }
                }
            }
        }

    }
}