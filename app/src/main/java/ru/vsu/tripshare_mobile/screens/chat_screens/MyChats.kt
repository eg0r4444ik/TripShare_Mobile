package ru.vsu.tripshare_mobile.screens.chat_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.NavGraph
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.services.ChatService
import ru.vsu.tripshare_mobile.services.UserService
import ru.vsu.tripshare_mobile.services.ValidationService
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.mint36

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MyChats(user: UserModel, navController: NavController){

    var chats = remember { mutableStateListOf<MutableState<ChatModel>>()}
    CoroutineScope(Dispatchers.Main).launch {
        val myChats = ChatService.getMyChats()
        myChats.onSuccess {
            myChats.getOrNull()?.forEach {
                chats.add(mutableStateOf(it))
            }
            AppConfig.currentChats = myChats.getOrNull()
        }.onFailure {

        }
    }

    Scaffold(
        content = {
            chats?.let {
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
                            ChatCard(item.value, user, navController)
                        }
                    }
                }
            } ?: run {
                CircularProgressIndicator()
            }
        }
    )
}