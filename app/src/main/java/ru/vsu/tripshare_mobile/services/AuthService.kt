package ru.vsu.tripshare_mobile.services

import android.security.keystore.UserNotAuthenticatedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import ru.vsu.tripshare_mobile.api.dto.users.RegistrationRequestDTO
import ru.vsu.tripshare_mobile.api.dto.users.RegistrationResponseDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.UserModel

object AuthService {

    fun registerUser(registrationRequestDTO: RegistrationRequestDTO) {
        try {
            AppConfig.retrofitAPI.registerUser(registrationRequestDTO)
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    suspend fun auth(username: String, password: String): Result<UserModel>{
        return withContext(Dispatchers.IO) {
            try {
                val token = AppConfig.retrofitAPI.getToken(
                    RequestBody.create("text/plain".toMediaTypeOrNull(), username),
                    RequestBody.create("text/plain".toMediaTypeOrNull(), password))
                AppConfig.authManager.saveToken(token.access_token)
                getUser()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getUser(): Result<UserModel>{
        if(AppConfig.authManager.getToken() == null){
            return Result.failure(UserNotAuthenticatedException())
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = AppConfig.retrofitAPI.getUser()
                val user = UserModel(
                    response.id,
                    response.name,
                    response.surname,
                    response.phone,
                    response.email,
                    response.birthday,
                    response.rating,
                    response.avatar_id,
                    mutableListOf(),
                    mutableListOf(),
                    response.musicPreferences,
                    response.talkativeness,
                    response.attitude_towards_smoking,
                    response.attitude_towards_animals_during_the_trip,
                    response.info
                )
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}