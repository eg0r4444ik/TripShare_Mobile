package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import ru.vsu.tripshare_mobile.api.dto.cars.CarDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.CarModel

object CarService {

    fun addCar(carModel: CarModel){
        try {
            val carDTO = fromModelToDTO(carModel)
            AppConfig.retrofitAPI.addCar(carDTO)
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    suspend fun getMyCars(): Result<List<CarModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val carsDTO = AppConfig.retrofitAPI.getMyCars()
                var cars = mutableListOf<CarModel>()
                carsDTO.forEach{cars.add(fromDTOtoModel(it))}
                Result.success(cars)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getUsersCars(userId: Int): Result<List<CarModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val carsDTO = AppConfig.retrofitAPI.getUsersCars(userId)
                var cars = mutableListOf<CarModel>()
                carsDTO.forEach{cars.add(fromDTOtoModel(it))}
                Result.success(cars)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


    private fun fromModelToDTO(carModel: CarModel): CarDTO {
        val carDTO = CarDTO(
            carModel.brand,
            carModel.model,
            carModel.color,
            carModel.manufactureYear,
            carModel.imageIds[0],
            carModel.imageIds[1],
            carModel.imageIds[2],
            carModel.imageIds[3]
        )
        return carDTO
    }

    private fun fromDTOtoModel(carDTO: CarDTO): CarModel {
        val car = CarModel(
            carDTO.brand,
            carDTO.model,
            carDTO.color,
            carDTO.year_of_manufacture,
            mutableListOf(carDTO.image0_id, carDTO.image1_id, carDTO.image2_id, carDTO.image3_id)
        )
        return car
    }

}