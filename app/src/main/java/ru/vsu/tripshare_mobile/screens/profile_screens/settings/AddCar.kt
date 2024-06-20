package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.CarModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.services.CarService
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyLightGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white18
import ru.vsu.tripshare_mobile.utils.ImageUtils

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddCar(user: UserModel, navController: NavController){

    //todo добавить валидацию текстовых полей
    val state = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state)
    ) {
        Text(text = "Добавление авто", style = mint24, modifier = Modifier.padding(10.dp, 0.dp))

        var imageUrl by remember { mutableStateOf<String?>(null) }

        val context = AppConfig.appContext
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
        }

        Button(
            modifier = Modifier
                .height(120.dp)
                .width(160.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MyLightGray),
            onClick = { launcher.launch("image/*") }
        ) {
            Text(text = "Добавить фото", style = darkGray18)
        }

        imageUri?.let {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }

            bitmap?.let {
                ImageUtils.saveCarImage(bitmap)
            }
        }

        imageUrl?.let {
            val painter: Painter = rememberImagePainter(imageUrl!!)
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

        var brand by remember { mutableStateOf("") }
        var model by remember { mutableStateOf("") }
        var color by remember { mutableStateOf("") }
        var manufactureYear by remember { mutableStateOf("") }

        TextField(
            value = brand,
            onValueChange = { newText ->
                brand = newText
            },
            label = { Text("Марка авто") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textStyle = darkGray18,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MyLightGray,
                focusedIndicatorColor = MyDarkGray,
                unfocusedIndicatorColor = MyDarkGray
            ),
            shape = RoundedCornerShape(15.dp)
        )

        TextField(
            value = model,
            onValueChange = { newText ->
                model = newText
            },
            label = { Text("Модель") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textStyle = darkGray18,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MyLightGray,
                focusedIndicatorColor = MyDarkGray,
                unfocusedIndicatorColor = MyDarkGray
            ),
            shape = RoundedCornerShape(15.dp)
        )

        TextField(
            value = color,
            onValueChange = { newText ->
                color = newText
            },
            label = { Text("Цвет") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textStyle = darkGray18,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MyLightGray,
                focusedIndicatorColor = MyDarkGray,
                unfocusedIndicatorColor = MyDarkGray
            ),
            shape = RoundedCornerShape(15.dp)
        )

        var manufactureYearError by remember { mutableStateOf(false) }

        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = manufactureYear,
                onValueChange = { newText ->
                    val number = newText.toIntOrNull()
                    if (number != null && number in 0..2024) {
                        manufactureYear = newText
                        manufactureYearError = false
                    } else {
                        if (newText.isEmpty()) {
                            manufactureYear = newText
                        }
                        manufactureYearError = true
                    }
                },
                label = { Text("Год выпуска") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = manufactureYearError,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                textStyle = darkGray18,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MyLightGray,
                    focusedIndicatorColor = MyDarkGray,
                    unfocusedIndicatorColor = MyDarkGray
                ),
                shape = RoundedCornerShape(15.dp)
            )

            if (manufactureYearError) {
                Text(
                    text = "Введите число от 1990 до 2024",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(10.dp, 5.dp)
                )
            }
        }

        Button(
            onClick = {
                val addCarEvent = "{\"button_clicked\":\"add_car\"}"
                AppMetrica.reportEvent(
                    "Adding car event",
                    addCarEvent
                )
                val car = CarModel(1, brand, model, color, manufactureYear.toInt(), AppConfig.currentCarImageUrl)
                AppConfig.currentCarImageUrl = null
                CoroutineScope(Dispatchers.Main).launch {
                    CarService.addCar(car)
                }
                navController.navigate("profile_screen")
            },
            colors = ButtonDefaults.buttonColors(containerColor = MyMint),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(20.dp, 0.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(text = "Добавить автомобиль", style = white18)
            }
        }
    }
}