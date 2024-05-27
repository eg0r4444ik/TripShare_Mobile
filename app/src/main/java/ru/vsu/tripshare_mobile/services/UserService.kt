package ru.vsu.tripshare_mobile.services

import android.security.keystore.UserNotAuthenticatedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.users.UserDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.UserModel

object UserService {

    suspend fun getUser(): Result<UserModel>{
        if(AppConfig.authManager.getToken() == null){
            return Result.failure(UserNotAuthenticatedException())
        }
        return withContext(Dispatchers.IO) {
            try {
                val userDTO = AppConfig.retrofitAPI.getUser()
                val user = fromDTOtoModel(userDTO)
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    fun updateUser(userModel: UserModel){
        try {
            val userDTO = fromModelToDTO(userModel)
            AppConfig.retrofitAPI.updateUser(userDTO)
            AppConfig.currentUser = userModel
        } catch (e: Exception) {
            e.stackTrace
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

    private fun fromDTOtoModel(userDTO: UserDTO): UserModel{
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