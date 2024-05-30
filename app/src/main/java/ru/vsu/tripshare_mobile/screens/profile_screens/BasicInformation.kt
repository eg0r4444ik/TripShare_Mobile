package ru.vsu.tripshare_mobile.screens.profile_screens

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.services.ImageService
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.darkGray36
import ru.vsu.tripshare_mobile.ui.theme.darkGray48
import ru.vsu.tripshare_mobile.ui.theme.mint18
import ru.vsu.tripshare_mobile.ui.theme.white18

@Composable
fun BasicInformation(user: UserModel, person: UserModel, navController: NavController){
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
                user.avatarId?.let { painterResource(id = it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = "image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
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
                    SelectPhoto()
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
                    //todo добавить добавление чата
                    onClick = { navController.navigate("chat") },
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

@Composable
fun SelectPhoto(){
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
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
                Toast.makeText(
                    AppConfig.appContext,
                    "Добавление фото пока недоступно",
                    Toast.LENGTH_SHORT
                ).show()
//                launcher.launch("image/*")
        })
        //todo отправить фото на бек
//        imageUri?.let {
//            val bitmap = if (Build.VERSION.SDK_INT < 28) {
//                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
//            } else {
//                val source = ImageDecoder.createSource(context.contentResolver, it)
//                ImageDecoder.decodeBitmap(source)
//            }
//
//            val id = ImageService.addImage(bitmap)
//            print(id)
//            bitmap?.let {
//                Image(bitmap = it.asImageBitmap(), contentDescription = null, modifier = Modifier.size(200.dp))
//            }
//        }
    }
}