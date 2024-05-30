package ru.vsu.tripshare_mobile.api

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.vsu.tripshare_mobile.api.dto.cars.CarDTO
import ru.vsu.tripshare_mobile.api.dto.chats.ChatDTO
import ru.vsu.tripshare_mobile.api.dto.chats.MessageDTO
import ru.vsu.tripshare_mobile.api.dto.reviews.ReviewDTO
import ru.vsu.tripshare_mobile.api.dto.trips.TripDTO
import ru.vsu.tripshare_mobile.api.dto.users.RegistrationDTO
import ru.vsu.tripshare_mobile.api.dto.users.TokenDTO
import ru.vsu.tripshare_mobile.api.dto.users.UserDTO

interface RetrofitApi {

    @POST("users/")
    fun registerUser(@Body registrationDTO: RegistrationDTO?): UserDTO

    @Multipart
    @POST("users/token")
    suspend fun getToken(@Part("username") username: RequestBody, @Part("password") password: RequestBody): TokenDTO

    @GET("users/me")
    suspend fun getMe(): UserDTO

    @PUT("users/me")
    fun updateMe(@Body userDTO: UserDTO): UserDTO

    @GET("users/{user_id}")
    suspend fun getUser(@Path("user_id") id: Int): UserDTO

    //-------------------------------------------------------------------------------------------------------------------------------------------

    @POST("trips/")
    suspend fun addTrip(@Body tripDTO: TripDTO): TripDTO

    @GET("trips/as_driver")
    suspend fun getTripsAsDriver(): List<TripDTO>

    @GET("trips/search")
    suspend fun findTrips(@Query("place_start") placeStart: String, @Query("place_end") placeEnd: String): List<TripDTO>

    //-------------------------------------------------------------------------------------------------------------------------------------------

    @POST("cars/")
    suspend fun addCar(@Body carDTO: CarDTO): CarDTO

    @GET("cars/me")
    suspend fun getMyCars(): List<CarDTO>

    @GET("cars/user/{user_id}")
    suspend fun getUsersCars(@Path("user_id") id: Int): List<CarDTO>

    //-------------------------------------------------------------------------------------------------------------------------------------------

    @POST("/images/")
    fun addImage(@Body base64Image: String): Int

    @GET("/images/{image_id}")
    suspend fun getImage(@Path("image_id") id: Int): String

    //-------------------------------------------------------------------------------------------------------------------------------------------

    @GET("chat/me")
    suspend fun getMyChats(): List<ChatDTO>

    @POST("chat/messages")
    fun addMessage(@Query("receiver_id") receiverId: Int, @Body messageDTO: MessageDTO): MessageDTO

    @GET("chat/{chat_id}/messages")
    suspend fun getChatMessages(@Path("chat_id") id: Int): List<MessageDTO>

    //-------------------------------------------------------------------------------------------------------------------------------------------

    @POST("reviews/")
    fun addReview(@Body reviewDTO: ReviewDTO): ReviewDTO

    @GET("reviews/{review_id}")
    suspend fun getReview(@Path("review_id") id: Int): ReviewDTO

    @DELETE("reviews/{review_id}")
    fun deleteReview(@Path("review_id") id: Int): String

    @GET("reviews/me")
    suspend fun getMyReviews(): List<ReviewDTO>

    @GET("reviews/user/{user_id}")
    suspend fun getUsersReviews(@Path("user_id") id: Int): List<ReviewDTO>

}