package ru.vsu.tripshare_mobile.char_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.User
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.mint36

@Composable
fun MyChats(chats: List<ChatModel>, user: User, navController: NavController){
    Column(
        modifier = Modifier.fillMaxWidth().padding(10.dp, 5.dp)
    ){
        Text(text = "Чаты", style = mint36)
        Divider(color = MyDarkGray, thickness = 1.dp)

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            itemsIndexed(chats) { _, item ->
                ChatCard(item, user, navController)
            }
        }
    }
}