package ru.vsu.tripshare_mobile.services

import android.security.keystore.UserNotAuthenticatedException
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.users.UserDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.UserModel

object UserService {

    suspend fun getMe(): Result<UserModel>{
        if(AppConfig.authManager.getToken() == null){
            return Result.failure(UserNotAuthenticatedException())
        }
        return withContext(Dispatchers.IO) {
            try {
                val userDTO = AppConfig.retrofitAPI.getMe()
                val user = fromDTOtoModel(userDTO)
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getUser(userId: Int): Result<UserModel>{
        if(AppConfig.authManager.getToken() == null){
            return Result.failure(UserNotAuthenticatedException())
        }
        return withContext(Dispatchers.IO) {
            try {
                val userDTO = AppConfig.retrofitAPI.getUser(userId)
                val user = fromDTOtoModel(userDTO)
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun updateMe(){
        try {
            val user = AppConfig.currentUser!!
            val userDTO = fromModelToDTO(user)
            AppConfig.retrofitAPI.updateMe(userDTO)
        } catch (e: Exception) {
            Toast.makeText(
                AppConfig.appContext,
                "Введенные данные некорректны",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun fromModelToDTO(userModel: UserModel): UserDTO{
        val userDTO = UserDTO(
            userModel.phone,
            userModel.name,
            userModel.surname,
            userModel.email,
            userModel.birthday,
            userModel.musicPreferences,
            userModel.info,
            userModel.talkativeness,
            userModel.attitudeTowardsSmoking,
            userModel.attitudeTowardsAnimals,
            userModel.avatarId
        )
        return userDTO
    }

    fun fromDTOtoModel(userDTO: UserDTO): UserModel{
        val user = UserModel(
            userDTO.id!!,
            userDTO.name,
            userDTO.surname,
            userDTO.phone,
            userDTO.email,
            userDTO.birthday,
            userDTO.rating,
            userDTO.avatar_id,
            userDTO.musicPreferences,
            userDTO.talkativeness,
            userDTO.attitude_towards_smoking,
            userDTO.attitude_towards_animals_during_the_trip,
            userDTO.info
        )
        return user
    }
}