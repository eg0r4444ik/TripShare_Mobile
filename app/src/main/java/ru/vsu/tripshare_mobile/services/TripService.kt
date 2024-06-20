package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.trips.FindTripRequestDTO
import ru.vsu.tripshare_mobile.api.dto.trips.FindTripResponseDTO
import ru.vsu.tripshare_mobile.api.dto.trips.StopDTO
import ru.vsu.tripshare_mobile.api.dto.trips.TripDTO
import ru.vsu.tripshare_mobile.api.dto.trips.TripStatusDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.StopModel
import ru.vsu.tripshare_mobile.models.TripModel
import ru.vsu.tripshare_mobile.models.TripStatus
import ru.vsu.tripshare_mobile.models.UserModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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
            var trip = mutableListOf<TripModel>()
            try {
                val tripDTO = AppConfig.retrofitAPI.getTripsAsDriver()
                tripDTO.forEach{
                    val tripModel = tripFromDTOtoModel(it)
                    if(tripModel.isSuccess && tripModel.getOrNull() != null) {
                        trip.add(tripModel.getOrNull()!!)
                    }
                }
                Result.success(trip)
            } catch (e: Exception) {
                Result.success(trip)
            }
        }
    }

    suspend fun getTrip(id: Int): Result<TripModel>{
        return withContext(Dispatchers.IO) {
            try {
                val tripDTO = AppConfig.retrofitAPI.getTrip(id)
                Result.success(tripFromDTOtoModel(tripDTO).getOrNull()!!)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun findTrips(requestDTO: FindTripRequestDTO): Result<List<FindTripResponseDTO>>{
        return withContext(Dispatchers.IO) {
            try {
                val responseDTO = AppConfig.retrofitAPI.findTrips(requestDTO)
                Result.success(responseDTO)
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
            stopsFromModelToDTO(tripModel.stops)
        )

        return tripDTO
    }

    suspend fun tripFromDTOtoModel(tripDTO: TripDTO): Result<TripModel>{
        return withContext(Dispatchers.IO) {
            try {
                val driver = ValidationService.validate(UserService.getUser(tripDTO.driver_id!!), "Пользователя не существует")
                val cityFrom = PlaceService.getPlace(tripDTO.stops.get(0).place_name).getOrNull()!!.address
                val cityTo = PlaceService.getPlace(tripDTO.stops.get(tripDTO.stops.size-1).place_name).getOrNull()!!.address

                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                val isoInstantDeparture = tripDTO.stops.get(0).datetime + "Z"
                val departureInstant = Instant.parse(isoInstantDeparture)
                val departureZonedDateTime = departureInstant.atZone(ZoneId.systemDefault())
                val departureDateTime = formatter.format(departureZonedDateTime)


                val isoInstantArrival = tripDTO.stops.get(tripDTO.stops.size-1).datetime + "Z"
                val arrivalInstant = Instant.parse(isoInstantArrival)
                val arrivalZonedDateTime = arrivalInstant.atZone(ZoneId.systemDefault())
                val arrivalDateTime = formatter.format(arrivalZonedDateTime)

                if (driver != null){
                    val trip = TripModel(
                        tripDTO.id!!,
                        cityFrom,
                        cityTo,
                        stopsFromDTOtoModel(tripDTO.stops),
                        "5 дней",
                        departureDateTime,
                        "",
                        arrivalDateTime,
                        "",
                        driver,
                        listOf<UserModel>(),
                        tripDTO.max_two_passengers_in_the_back_seat,
                        tripDTO.smoking_allowed,
                        tripDTO.pets_allowed,
                        tripDTO.free_trunk,
                        tripDTO.car_id,
                        if(tripDTO.driver_id == AppConfig.currentUser!!.id) TripStatus.DRIVER else statusFromDTOtoModel(tripDTO.status!!),
                        tripDTO.cost_sum,
                        tripDTO.max_passengers
                    )
                    Result.success(trip)
                }else{
                    Result.failure(Exception())
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun stopsFromModelToDTO(stops: List<StopModel>): List<StopDTO>{
        var stopsDTO = mutableListOf<StopDTO>()
        var curr = 0
        stops.forEach{
            val inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            val localDateTime = LocalDateTime.parse(it.date + " " + it.time, inputFormatter)
            val instant = localDateTime.toInstant(ZoneOffset.UTC)
            val isoFormatter = DateTimeFormatter.ISO_INSTANT
            val iso8601String = isoFormatter.format(instant)
            stopsDTO.add(
                StopDTO(
                    it.placeName,
                    it.placeName,
                    iso8601String,
                    curr)
            )
            curr++
        }

        return stopsDTO
    }

    private fun stopsFromDTOtoModel(stopsDTO: List<StopDTO>): List<StopModel>{
        var stops = mutableListOf<StopModel>()
        stopsDTO.forEach{
            stops.add(StopModel(it.place_name, "", ""))
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