package ru.vsu.tripshare_mobile.profile_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.User
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.darkGray36
import ru.vsu.tripshare_mobile.ui.theme.white18

@Composable
fun Cars(user: User, navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Ваши авто", style = darkGray36, modifier = Modifier.padding(10.dp))

            if(user.cars != null) {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    itemsIndexed(user.cars) { _, item ->
                        Column(
                            modifier = Modifier.padding(10.dp, 10.dp).clickable { navController.navigate("add_car") },
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painterResource(id = item.imageIds.get(0)),
                                contentDescription = "car",
                                modifier = Modifier.size(160.dp, 120.dp)
                            )
                            Text(text = item.model + " " + item.brand, style = darkGray14)
                        }
                    }
                }
            }else{
                Text(text = "У вас нет машин", style = darkGray36)
            }

            Button(
                onClick = { navController.navigate("add_car") },
                colors = ButtonDefaults.buttonColors(containerColor = MyMint),
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(20.dp, 0.dp)
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