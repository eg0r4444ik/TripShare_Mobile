package ru.vsu.tripshare_mobile.screens.payments_screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white14

@Composable
fun AddPayment(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Добавление карты",
            style = mint24
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(10.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ) {
            var cardNumber by remember { mutableStateOf("") }
            var cardName by remember { mutableStateOf("") }
            var expiryDate by remember { mutableStateOf("") }
            var cvv by remember { mutableStateOf("") }

            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { if(it.length <= 24) cardNumber = it },
                    label = { Text("Номер карты") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = cardName,
                    onValueChange = { cardName = it },
                    label = { Text("Имя на карте") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = { expiryDate = it },
                        label = { Text("MM/YY") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { if(it.length <= 3) cvv = it },
                        label = { Text("CVV") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if(cvv.length != 3){
                            Toast.makeText(
                            AppConfig.appContext,
                            "Неправильный cvv",
                            Toast.LENGTH_LONG
                            ).show()
                        }else if(cardNumber.length < 12){
                            Toast.makeText(
                                AppConfig.appContext,
                                "Неправильный номер карты",
                                Toast.LENGTH_LONG
                            ).show()
                        }else if(expiryDate.length != 5 || expiryDate[2] != '/'){
                            Toast.makeText(
                                AppConfig.appContext,
                                "Заполните поле в формате MM/YY",
                                Toast.LENGTH_LONG
                            ).show()
                        }else {
                            navController.navigate("profile_screen")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MyMint),
                    elevation = ButtonDefaults.buttonElevation(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(10.dp)
                ) {
                    Text(text = "Добавить", style = white14)
                }
            }
        }
    }
}