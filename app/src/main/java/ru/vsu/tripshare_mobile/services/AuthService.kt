package ru.vsu.tripshare_mobile.services

import android.security.keystore.UserNotAuthenticatedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import ru.vsu.tripshare_mobile.api.dto.users.RegistrationDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.UserModel

object AuthService {

    suspend fun registerUser(registrationRequestDTO: RegistrationDTO) {
        return withContext(Dispatchers.IO) {
            try {
                AppConfig.retrofitAPI.registerUser(registrationRequestDTO)
            } catch (e: Exception) {
            }
        }
    }

    suspend fun auth(username: String, password: String): Result<UserModel>{
        return withContext(Dispatchers.IO) {
            try {
                val token = AppConfig.retrofitAPI.getToken(
                    RequestBody.create("text/plain".toMediaTypeOrNull(), username),
                    RequestBody.create("text/plain".toMediaTypeOrNull(), password))
                AppConfig.authManager.saveToken(token.access_token)
                UserService.getMe()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}