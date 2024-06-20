package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.ReviewModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.services.ReviewService
import ru.vsu.tripshare_mobile.services.UserService
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyLightGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white18

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun AddReview(userId: Int, navController: NavController){

    var user by remember { mutableStateOf<UserModel?>(null) }

    LaunchedEffect(Unit) {
        val userResult = UserService.getUser(userId)
        if(userResult.isSuccess) {
            user = userResult.getOrNull()
        }
    }

    Scaffold(
        content = {
            if (user != null) {
                val state = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state)
                ) {
                    Text(
                        text = "Ваш отзыв",
                        style = mint24,
                        modifier = Modifier.padding(10.dp, 0.dp)
                    )

                    var rating by remember { mutableStateOf("") }
                    var text by remember { mutableStateOf("") }

                    var ratingError by remember { mutableStateOf(false) }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = rating,
                            onValueChange = { newText ->
                                val number = newText.toIntOrNull()
                                if (number != null && number in 0..5) {
                                    rating = newText
                                    ratingError = false
                                } else {
                                    if (newText.isEmpty()) {
                                        rating = newText
                                    }
                                    ratingError = true
                                }
                            },
                            label = { Text("Оценка (0-5)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = ratingError,
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            textStyle = darkGray18,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MyLightGray,
                                focusedIndicatorColor = MyDarkGray,
                                unfocusedIndicatorColor = MyDarkGray
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )

                        if (ratingError) {
                            Text(
                                text = "Введите число от 0 до 5",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(10.dp, 5.dp)
                            )
                        }
                    }

                    TextField(
                        value = text,
                        onValueChange = { newText ->
                            text = newText
                        },
                        label = { Text("Текст отзыва") },
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

                    Button(
                        onClick = {
                            val addReviewEvent = "{\"button_clicked\":\"add_review\"}"
                            AppMetrica.reportEvent(
                                "Adding review event",
                                addReviewEvent
                            )
                            var rate = 0
                            try {
                                rate = rating.toInt()
                                if (rate < 0 || rate > 5) {
                                    Toast.makeText(
                                        AppConfig.appContext,
                                        "Данные некорректны рейтинг должен быть числом от 0 до 5",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(
                                    AppConfig.appContext,
                                    "Данные некорректны рейтинг должен быть числом от 0 до 5",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            val review = ReviewModel(user!!, rate, text)
                            CoroutineScope(Dispatchers.Main).launch {
                                ReviewService.addReview(review)
                            }
                            navController.navigate("reviews/${user!!.id}")
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
                            Text(text = "Добавить отзыв", style = white18)
                        }
                    }
                }
            }
        }
    )
}