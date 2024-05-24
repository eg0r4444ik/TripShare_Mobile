package ru.vsu.tripshare_mobile.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.vsu.tripshare_mobile.api.dto.RegistrationRequestDTO
import ru.vsu.tripshare_mobile.api.dto.RegistrationResponseDTO
import ru.vsu.tripshare_mobile.api.dto.TokenDTO
import ru.vsu.tripshare_mobile.api.dto.UserDTO

interface RetrofitApi {

    @POST("users/")
    fun registerUser(@Body registrationRequestModel: RegistrationRequestDTO?): RegistrationResponseDTO

    @Multipart
    @POST("users/token")
    suspend fun getToken(@Part("username") username: RequestBody, @Part("password") password: RequestBody): TokenDTO

    @GET("users/me")
    suspend fun getUser(): UserDTO

}