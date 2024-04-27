package ru.vsu.tripshare_mobile.chat_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.vsu.tripshare_mobile.models.MessageModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray12
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.white12
import ru.vsu.tripshare_mobile.ui.theme.white14

const val BIG_TEXT_LEN = 30

@Composable
fun Message(message: MessageModel, user: UserModel){

    val messageSize = if(message.text.length >= BIG_TEXT_LEN) 0.6f else 0.4f

    // поменять сравнение юзеров
    if(message.sender.email == user.email){
        Box(modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(messageSize).padding(0.dp, 10.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MyMint,
                ),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp, 10.dp, 10.dp, 0.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = message.text, style = white14)
                }

                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Text(text = message.time.hours.toString() + ":" +  if(message.time.minutes < 10) "0" + message.time.minutes.toString()
                    else "" + message.time.minutes.toString(), style = white12)
                }
            }
        }
    }else{
        Box(modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(messageSize).padding(0.dp, 10.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp, 10.dp, 10.dp, 0.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = message.text, style = darkGray14)
                }

                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Text(text = message.time.hours.toString() + ":" +  if(message.time.minutes < 10) "0" + message.time.minutes.toString()
                    else "" + message.time.minutes.toString(), style = darkGray12)
                }
            }
        }
    }
}