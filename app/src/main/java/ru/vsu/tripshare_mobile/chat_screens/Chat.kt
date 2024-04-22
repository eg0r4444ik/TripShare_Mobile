package ru.vsu.tripshare_mobile.chat_screens

import android.os.Messenger
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.models.User
import ru.vsu.tripshare_mobile.trips_screens.MyTripAsDriver
import ru.vsu.tripshare_mobile.trips_screens.MyTripAsPassenger
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.black18
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.utils.DataUtils
import java.util.Date

@Composable
fun Chat(chat: ChatModel, user: User, navController: NavController){

    Column(){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(10.dp, 10.dp),
            verticalAlignment = Alignment.Top,
        ){
            Image(
                //todo заменить !! на проверку на null
                painter = painterResource(id = chat.companion.imageId!!),
                contentDescription = "companion",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = chat.companion.surname + " " + chat.companion.name,
                    style = black18
                )
            }
        }

        Divider(color = MyDarkGray, thickness = 1.dp)

        LazyColumn(
            modifier = Modifier.fillMaxSize()
            // todo Поставить начальное положение вниз
        ) {
            var curr = Date()
            itemsIndexed(chat.messages) { _, item ->
                if(!curr.equals(item.time)){
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(10.dp, 10.dp),
                        contentAlignment = Alignment.Center
                    ){
                        //заменить на статическую функцию
                        val du = DataUtils()
                        Text(text = du.dateToString(item.time),
                            style = blue18)
                    }
                }
                Message(item, user)
                curr = item.time
            }
        }

    }

}