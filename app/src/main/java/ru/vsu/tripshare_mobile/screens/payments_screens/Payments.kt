package ru.vsu.tripshare_mobile.screens.payments_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.api.dto.payments.PaymentDTO
import ru.vsu.tripshare_mobile.api.dto.payments.PaymentStatusDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.services.PaymentService
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyRed
import ru.vsu.tripshare_mobile.ui.theme.blue18
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint36
import ru.vsu.tripshare_mobile.ui.theme.white14
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun Payments(navController: NavController){

    var payments by remember { mutableStateOf(emptyList<PaymentDTO>()) }

    LaunchedEffect(Unit) {
        val paymentsResult = PaymentService.getPayments()
        if(paymentsResult.isSuccess) {
            payments = paymentsResult.getOrNull()!!
        }
    }

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 5.dp)
            ){
                Text(text = "История платежей", style = mint36)
                Divider(color = MyDarkGray, thickness = 1.dp)

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    itemsIndexed(payments) { _, item ->
                        Spacer(modifier = Modifier.height(10.dp))
                        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                        val isoInstantDeparture = item.datetime_fn
                        val instant = Instant.parse(isoInstantDeparture)
                        val zonedDateTime = instant.atZone(ZoneId.systemDefault())
                        val dateTime = formatter.format(zonedDateTime)

                        if(item.from_user_id == AppConfig.currentUser!!.id) {
                            Row(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                PaymentStatus(item.status)
                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text(text = "-" + item.amount, style = blue18)
                                    Text(text = dateTime, style = darkGray18)
                                }
                            }
                        }else{
                            Row(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                PaymentStatus(item.status)
                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text(text = "+" + item.amount, style = blue18)
                                    Text(text = dateTime, style = darkGray18)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun PaymentStatus(status: PaymentStatusDTO){
    Card(shape = RoundedCornerShape(15.dp), modifier = Modifier.padding(10.dp)){
        if(status == PaymentStatusDTO.CREATED){
            PaymentStatus(text = "В ожидании", color = MyDarkGray)
        }else if(status == PaymentStatusDTO.OK){
            PaymentStatus(text = "Успешно", color = Color.Green)
        }else if(status == PaymentStatusDTO.ERROR){
            PaymentStatus(text = "Ошибка операции", color = MyRed)
        }else if(status == PaymentStatusDTO.RETURNED){
            PaymentStatus(text = "Возвращено", color = MyBlue)
        }
    }
}

@Composable
private fun PaymentStatus(text: String, color: Color){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color)
            .fillMaxWidth()
            .height(34.dp)
    ) {
        Text(text = text, style = white14)
    }
}