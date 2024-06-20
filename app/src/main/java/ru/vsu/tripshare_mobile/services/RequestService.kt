package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.requests.RequestDTO
import ru.vsu.tripshare_mobile.config.AppConfig

object RequestService {

    suspend fun getMyRequests(): Result<List<RequestDTO>>{
        return withContext(Dispatchers.IO) {
            try {
                val requestsDTO = AppConfig.retrofitAPI.getMyRequests()
                Result.success(requestsDTO)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun addRequest(requestDTO: RequestDTO){
        try {
            AppConfig.retrofitAPI.addRequest(requestDTO)
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    suspend fun existRequest(tripId: Int): Boolean{
        val requests = getMyRequests().getOrNull()
        requests?.forEach {
            if(it.trip_id == tripId){
                return true
            }
        }

        return false
    }

}