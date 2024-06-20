package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.requests.RequestDTO
import ru.vsu.tripshare_mobile.api.dto.requests.RequestStatusDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.RequestModel
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.models.UserModel

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

    suspend fun getTripRequests(id: Int): Result<List<RequestModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val requestsDTO = AppConfig.retrofitAPI.getTripRequests(id)
                var requests = mutableListOf<RequestModel>()
                requestsDTO.forEach{
                    val user = UserService.getUser(it.user_id!!).getOrNull()!!
                    requests.add(RequestModel(
                        it.number_of_seats,
                        it.departure_id,
                        it.arrival_id,
                        it.trip_id,
                        it.cost,
                        user,
                        it.id,
                        it.request_datetime,
                        it.status,
                        it.status_change_datetime,
                        it.trip
                    ))
                }
                Result.success(requests)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getParticipants(id: Int): List<UserModel>{
        val requestDTO = getTripRequests(id).getOrNull()
        val users = mutableListOf<UserModel>()
        requestDTO?.forEach {
            if(it.status == RequestStatusDTO.ACCEPTED){
                users.add(it.user!!)
            }
        }
        return users
    }

    suspend fun getMyTripsAsPassenger(): List<TripModel>{
        val requestsDTO = getMyRequests().getOrNull()
        val trips = mutableListOf<TripModel>()
        requestsDTO?.forEach {
            val trip = TripService.tripFromDTOtoModel(it.trip!!)
            if(trip.isSuccess) {
                if(it.status == RequestStatusDTO.CREATED){
                    trip.getOrNull()!!.status = TripStatus.PENDING
                }else if(it.status == RequestStatusDTO.ACCEPTED){
                    trip.getOrNull()!!.status = TripStatus.PASSENGER
                }else if(it.status == RequestStatusDTO.DECLINED){
                    trip.getOrNull()!!.status = TripStatus.REJECTED
                }else if(it.status == RequestStatusDTO.PAYED){
                    trip.getOrNull()!!.status = TripStatus.PASSENGER
                }else if(it.status == RequestStatusDTO.FINISHED){
                    trip.getOrNull()!!.status = TripStatus.FINISHED
                }else if(it.status == RequestStatusDTO.ERROR){
                    trip.getOrNull()!!.status = TripStatus.REJECTED
                }
                trips.add(trip.getOrNull()!!)
            }
        }

        return trips
    }

    suspend fun acceptRequest(id: Int){
        try {
            AppConfig.retrofitAPI.acceptRequest(id)
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    suspend fun declineRequest(id: Int){
        try {
            AppConfig.retrofitAPI.declineRequest(id)
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    suspend fun finishTrip(id: Int){
        try {
            AppConfig.retrofitAPI.finishTrip(id)
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    suspend fun errorTrip(id: Int){
        try {
            AppConfig.retrofitAPI.errorTrip(id)
        } catch (e: Exception) {
            e.stackTrace
        }
    }

}