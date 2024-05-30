package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.trips.StopDTO
import ru.vsu.tripshare_mobile.api.dto.trips.TripDTO
import ru.vsu.tripshare_mobile.api.dto.trips.TripStatusDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.models.UserModel
import java.util.Date

object TripService {

    suspend fun addTrip(tripModel: TripModel){
        return withContext(Dispatchers.IO) {
            try {
                val tripDTO = tripFromModelToDTO(tripModel)
                AppConfig.retrofitAPI.addTrip(tripDTO)
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    suspend fun getTripsAsDriver(): Result<List<TripModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val tripDTO = AppConfig.retrofitAPI.getTripsAsDriver()
                var trip = mutableListOf<TripModel>()
                tripDTO.forEach{
                    trip.add(tripFromDTOtoModel(it))
                }
                Result.success(trip)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun findTrips(placeStart: String, placeEnd: String): Result<List<TripModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val tripDTO = AppConfig.retrofitAPI.findTrips(placeStart, placeEnd)
                var trip = mutableListOf<TripModel>()
                tripDTO.forEach{
                    trip.add(tripFromDTOtoModel(it))
                }
                Result.success(trip)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun tripFromModelToDTO(tripModel: TripModel): TripDTO{
        val tripDTO = TripDTO(
            4,
            tripModel.cost,
            tripModel.maxTwoPassengersInTheBackSeat,
            tripModel.smokingAllowed,
            tripModel.petsAllowed,
            tripModel.freeTrunk,
            1,
            stopsFromModelToDTO(tripModel.addresses)
        )

        return tripDTO
    }

    private fun tripFromDTOtoModel(tripDTO: TripDTO): TripModel{
        var tripModel: TripModel? = null

        CoroutineScope(Dispatchers.Main).launch {
            val driver = ValidationService.validate(UserService.getUser(tripDTO.driver_id!!), "Пользователя не существует")
            if (driver != null){
                tripModel = TripModel(
                    tripDTO.id!!,
                    tripDTO.stops.get(0).place_name,
                    tripDTO.stops.get(tripDTO.stops.size).place_name,
                    stopsFromDTOtoModel(tripDTO.stops),
                    "5 дней",
                    "",
                    "",
                    "",
                   "",
                    driver,
                    listOf<UserModel>(),
                    tripDTO.max_two_passengers_in_the_back_seat,
                    tripDTO.smoking_allowed,
                    tripDTO.pets_allowed,
                    tripDTO.free_trunk,
                    null,
                    statusFromDTOtoModel(tripDTO.status!!),
                    tripDTO.cost_sum
                )
            }
        }

        return tripModel!!
    }

    private fun stopsFromModelToDTO(stops: List<String>): List<StopDTO>{
        var stopsDTO = mutableListOf<StopDTO>()
        var curr = 1
        stops.forEach{
            stopsDTO.add(
                StopDTO(
                    "55.75972°",
                    it,
                    Date(),
                    curr)
            )
            curr++
        }

        return stopsDTO
    }

    private fun stopsFromDTOtoModel(stopsDTO: List<StopDTO>): List<String>{
        var stops = mutableListOf<String>()
        stopsDTO.forEach{
            stops.add(it.place_name)
        }
        return stops
    }

    private fun statusFromDTOtoModel(tripStatusDTO: TripStatusDTO): TripStatus{
        if(tripStatusDTO == TripStatusDTO.NEW){
            return TripStatus.WITHOUT_STATUS
        }
        if(tripStatusDTO == TripStatusDTO.BRONED){
            return TripStatus.PENDING
        }
        if(tripStatusDTO == TripStatusDTO.FULLY_BRONNED){
            return TripStatus.REJECTED
        }
        if(tripStatusDTO == TripStatusDTO.IN_PROGRESS){
            return TripStatus.PASSENGER
        }
        return TripStatus.PASSENGER
    }

    private fun statusFromModelToDTO(tripStatus: TripStatus): TripStatusDTO{
        if(tripStatus == TripStatus.DRIVER || tripStatus == TripStatus.WITHOUT_STATUS){
            return TripStatusDTO.NEW
        }
        if(tripStatus == TripStatus.REJECTED){
            return TripStatusDTO.FULLY_BRONNED
        }
        if(tripStatus == TripStatus.PENDING){
            return TripStatusDTO.BRONED
        }
        return TripStatusDTO.IN_PROGRESS
    }

}