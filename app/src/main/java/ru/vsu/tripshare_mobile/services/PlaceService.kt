package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.PlaceModel

object PlaceService {

    suspend fun suggestPlace(address: String): Result<List<PlaceModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val placesDTO = AppConfig.retrofitAPI.suggestPlace(address)
                val places = mutableListOf<PlaceModel>()
                placesDTO.forEach{
                    places.add(PlaceModel(it.address, it.id))
                }
                Result.success(places)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getPlace(id: String): Result<PlaceModel>{
        return withContext(Dispatchers.IO) {
            try {
                val placeDTO = AppConfig.retrofitAPI.getPlace(id.toInt())
                val place = PlaceModel(placeDTO.region, placeDTO.id)
                Result.success(place)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}