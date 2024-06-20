package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.payments.PaymentDTO
import ru.vsu.tripshare_mobile.config.AppConfig

object PaymentService {

    suspend fun getPayments(): Result<List<PaymentDTO>>{
        return withContext(Dispatchers.IO) {
            try {
                val payments = AppConfig.retrofitAPI.getPayments()
                Result.success(payments)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}