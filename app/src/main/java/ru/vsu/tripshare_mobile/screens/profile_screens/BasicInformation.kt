package ru.vsu.tripshare_mobile.screens.profile_screens

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.darkGray36
import ru.vsu.tripshare_mobile.ui.theme.darkGray48
import ru.vsu.tripshare_mobile.ui.theme.mint18
import ru.vsu.tripshare_mobile.ui.theme.white18
import ru.vsu.tripshare_mobile.utils.ImageUtils

@Composable
fun BasicInformation(user: UserModel, person: UserModel, navController: NavController){

    var avatarUrl by remember { mutableStateOf<String?>(user.avatarUrl) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ){

        Row {

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                if(user.name.length <= 6) {
                    Text(text = user.name, style = darkGray48)
                }else{
                    Text(text = user.name, style = darkGray36)
                }
                Text(text = user.phone, style = darkGray18)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                if(avatarUrl == null) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painterResource(id = R.drawable.baseline_person),
                            contentDescription = "image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .border(1.dp, MyDarkGray, CircleShape)
                        )
                    }
                }else{
                    val painter: Painter = rememberImagePainter(avatarUrl!!)
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = "Image from URL",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .border(1.dp, MyDarkGray, CircleShape)
                        )
                    }
                }
            }
        }


        if(user.id == person.id) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomStart,
            ) {
                Column {
                    val context = AppConfig.appContext
                    var imageUri by remember { mutableStateOf<Uri?>(null) }
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                    ) { uri: Uri? ->
                        imageUri = uri
                    }

                    Column{
                        Text(
                            text = "Изменить фото профиля",
                            style = mint18,
                            modifier = Modifier.clickable {
                                launcher.launch("image/*")
                            }
                        )
                        imageUri?.let {
                            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                            } else {
                                val source = ImageDecoder.createSource(context.contentResolver, it)
                                ImageDecoder.decodeBitmap(source)
                            }

                            bitmap?.let {
                                ImageUtils.saveUserImage(bitmap)
                                avatarUrl = AppConfig.currentUser!!.avatarUrl
                            }
                        }
                    }
                    Text(
                        text = "Редактировать информацию о себе",
                        style = mint18,
                        modifier = Modifier.clickable {
                            navController.navigate("edit_info")
                        })
                    Text(
                        text = "Настройки аккаунта",
                        style = blue18,
                        modifier = Modifier.clickable {
                            navController.navigate("settings")
                        })
                }
            }
        }else{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Button(
                    onClick = { navController.navigate("new_chat/${user.id}") },
                    colors = ButtonDefaults.buttonColors(containerColor = MyBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(20.dp, 0.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Написать", style = white18)
                    }
                }
            }
        }
    }
}