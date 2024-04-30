package ru.vsu.tripshare_mobile.trips_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.TripParticipantModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.darkGray48
import ru.vsu.tripshare_mobile.ui.theme.mint36

@Composable
fun TripDetails(tripParticipant: TripParticipantModel, person: UserModel, navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Details")
    }

//    val state = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .background(Color.White)
//            .verticalScroll(state),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Детали поездки",
//            style = mint36
//        )
//
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(120.dp)
//                .padding(10.dp)
//                .clickable { navController.navigate("user_profile/${tripParticipant.trip.driver.id}") },
//            shape = RoundedCornerShape(15.dp),
//            elevation = CardDefaults.cardElevation(
//                defaultElevation = 5.dp
//            ),
//            colors = CardDefaults.cardColors(
//                containerColor = Color.White,
//            )
//        ) {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceAround
//            ){
//
//                Text(text = tripParticipant.trip.driver.name, style = darkGray48)
//
//                Image(
//                    //todo заменить !! на проверку на null
//                    painter = painterResource(id = tripParticipant.trip.driver.imageId!!),
//                    contentDescription = "driver",
//                    modifier = Modifier
//                        .size(80.dp)
//                        .clip(CircleShape)
//                )
//            }
//        }
//
//
//    }
}