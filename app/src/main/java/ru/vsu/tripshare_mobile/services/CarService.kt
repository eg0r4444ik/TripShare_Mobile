package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.cars.CarDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.CarModel

object CarService {

    suspend fun addCar(carModel: CarModel){
        return withContext(Dispatchers.IO) {
            try {
                val carDTO = fromModelToDTO(carModel)
                AppConfig.retrofitAPI.addCar(carDTO)
            } catch (e: Exception) {
                e.stackTrace
            }
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
            carModel.imageUrl
        )
        return carDTO
    }

    private fun fromDTOtoModel(carDTO: CarDTO): CarModel {
        val car = CarModel(
            carDTO.id!!,
            carDTO.brand,
            carDTO.model,
            carDTO.color,
            carDTO.year_of_manufacture,
            carDTO.iamges_url
        )
        return car
    }

}