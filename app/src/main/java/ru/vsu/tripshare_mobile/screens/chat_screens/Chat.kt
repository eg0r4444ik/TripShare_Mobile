package ru.vsu.tripshare_mobile.screens.chat_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.MessageModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.black18
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.white14
import ru.vsu.tripshare_mobile.utils.DataUtils.dateToString
import java.util.Date

@Composable
fun Chat(chat: ChatModel, user: UserModel, navController: NavController){

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(10.dp, 10.dp)
                .clickable { navController.navigate("user_profile/${chat.companion.id}") },
            verticalAlignment = Alignment.Top,
        ){
            Image(
                //todo заменить !! на проверку на null
                painter = painterResource(id = chat.companion.avatarId!!),
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

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .verticalScroll(scrollState)
                .background(Color.White)
                .padding(16.dp)
        ) {
            var curr = Date()
            curr.month++;
            chat.messages.forEach {
                if(curr.month != it.time.month || curr.date != curr.date){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 10.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = dateToString(it.time),
                            style = blue18)
                    }
                }
                Message(it, user)
                curr = it.time

                LaunchedEffect(Unit) {
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            }
        }

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(5.dp)
        ) {
            var text by remember { mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                label = { Text("Сообщение") },
                modifier = Modifier.fillMaxSize(),
                textStyle = white14,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MyBlue,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(onSend = {
                    chat.messages.add(MessageModel(user, text, false, Date()))
                    text = ""
                }),
                shape = RoundedCornerShape(15.dp)
            )
        }
    }
}