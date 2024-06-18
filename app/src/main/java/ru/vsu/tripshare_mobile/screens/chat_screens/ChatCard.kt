package ru.vsu.tripshare_mobile.screens.chat_screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.black18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18

const val BIG_MESSAGE_LEN = 40
@Composable
fun ChatCard(chat: ChatModel, unread: List<ChatModel>, user: UserModel, navController: NavController){

    val companion = if(chat.companion.id != user.id) chat.companion else chat.user

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(0.dp, 10.dp)
            .clickable { navController.navigate("chat" + "/${chat.id}" )},
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(10.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = if(companion.avatarId == null) R.drawable.baseline_person else companion.avatarId!!),
                contentDescription = "companion",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )

            Column(modifier = Modifier.padding(10.dp, 0.dp)){
                Text(text = companion.surname + " " + companion.name, style = black18)
                val last = chat.messages.get(chat.messages.size-1)
                val sender = if(last.senderId == user.id) user else (if(companion.id == user.id) chat.user else companion)
                // поменять сравнение юзеров
                Text(text = if(sender.email == user.email) "Вы: " + last.text else companion.name
                        + ": " + (if(last.text.length + sender.name.length >= BIG_MESSAGE_LEN)
                                (last.text.subSequence(0, BIG_MESSAGE_LEN -sender.name.length).toString() + "...") else last.text),
                    style = darkGray18)
            }

            if(hasUnreadMessages(chat, unread)){
                Box(
                    modifier = Modifier.fillMaxSize().padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ){
                    Canvas(modifier = Modifier.size(20.dp)) {
                        drawCircle(color = Color.Red, radius = size.minDimension / 2)
                    }
                }
            }
        }
    }
}

private fun hasUnreadMessages(chat: ChatModel, unread: List<ChatModel>): Boolean {
    unread.forEach{
        if(it.id == chat.id){
            return true
        }
    }
    return false
}