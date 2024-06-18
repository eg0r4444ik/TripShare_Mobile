package ru.vsu.tripshare_mobile.screens.chat_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.services.ChatService
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.mint36

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MyChats(user: UserModel, navController: NavController){

    var chats by remember { mutableStateOf(emptyList<ChatModel>()) }
    var unread by remember { mutableStateOf(emptyList<ChatModel>()) }

    LaunchedEffect(Unit) {
        val chatsResult = ChatService.getMyChats()
        if(chatsResult.isSuccess) {
            chats = AppConfig.currentChats!!
            unread = chatsResult.getOrNull()!!
        }
    }

    Scaffold(
        content = {
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
                        ChatCard(item, unread, user, navController)
                    }
                }
            }
        }
    )
}