package ru.vsu.tripshare_mobile.char_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.User
import ru.vsu.tripshare_mobile.ui.theme.black18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18

@Composable
fun ChatCard(chat: ChatModel, user: User, navController: NavController){
    Card(
        modifier = Modifier.fillMaxWidth().height(100.dp).padding(0.dp, 10.dp).clickable { navController.navigate("chat") },
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
                //todo заменить !! на проверку на null
                painter = painterResource(id = chat.companion.imageId!!),
                contentDescription = "companion",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )

            Column(modifier = Modifier.padding(10.dp, 0.dp)){
                Text(text = chat.companion.surname + " " + chat.companion.name, style = black18)
                val last = chat.messages.get(chat.messages.size-1)
                Text(text = if(last.sender.email == user.email) "Вы: " + last.text else chat.companion.name + ": " + last.text, style = darkGray18)
            }
        }
    }
}