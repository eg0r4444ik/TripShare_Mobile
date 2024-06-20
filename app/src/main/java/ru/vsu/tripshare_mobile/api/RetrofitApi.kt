package ru.vsu.tripshare_mobile.api

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ru.vsu.tripshare_mobile.api.dto.cars.CarDTO
import ru.vsu.tripshare_mobile.api.dto.chats.ChatDTO
import ru.vsu.tripshare_mobile.api.dto.chats.MessageDTO
import ru.vsu.tripshare_mobile.api.dto.payments.PaymentDTO
import ru.vsu.tripshare_mobile.api.dto.places.PlaceDTO
import ru.vsu.tripshare_mobile.api.dto.requests.RequestDTO
import ru.vsu.tripshare_mobile.api.dto.reviews.ReviewDTO
import ru.vsu.tripshare_mobile.api.dto.trips.FindTripRequestDTO
import ru.vsu.tripshare_mobile.api.dto.trips.FindTripResponseDTO
import ru.vsu.tripshare_mobile.api.dto.trips.TripDTO
import ru.vsu.tripshare_mobile.api.dto.users.RegistrationDTO
import ru.vsu.tripshare_mobile.api.dto.users.TokenDTO
import ru.vsu.tripshare_mobile.api.dto.users.UserDTO

interface RetrofitApi {

    @POST("users/")
    suspend fun registerUser(@Body registrationDTO: RegistrationDTO?): UserDTO

    @Multipart
    @POST("users/token")
    suspend fun getToken(@Part("username") username: RequestBody, @Part("password") password: RequestBody): TokenDTO

    @GET("users/me")
    suspend fun getMe(): UserDTO

    @POST("users/me")
    suspend fun updateMe(@Body userDTO: UserDTO): UserDTO

    @GET("users/{user_id}")
    suspend fun getUser(@Path("user_id") id: Int): UserDTO

    //-------------------------------------------------------------------------------------------------------------------------------------------

    @POST("trips/")
    suspend fun addTrip(@Body tripDTO: TripDTO): TripDTO

    @GET("trips/me_as_driver")
    suspend fun getTripsAsDriver(): List<TripDTO>

    @GET("trips/{id}")
    suspend fun getTrip(@Path("id") id: Int): TripDTO

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
    suspend fun addMessage(@Body messageDTO: MessageDTO): MessageDTO

    @GET("chat/{chat_id}/messages")
    suspend fun getChatMessages(@Path("chat_id") id: Int): List<MessageDTO>

    //-------------------------------------------------------------------------------------------------------------------------------------------

    @POST("reviews/")
    suspend fun addReview(@Body reviewDTO: ReviewDTO): ReviewDTO

    @GET("reviews/{review_id}")
    suspend fun getReview(@Path("review_id") id: Int): ReviewDTO

    @DELETE("reviews/{review_id}")
    fun deleteReview(@Path("review_id") id: Int): String

    @GET("reviews/me")
    suspend fun getMyReviews(): List<ReviewDTO>

    @GET("reviews/user/{user_id}")
    suspend fun getUsersReviews(@Path("user_id") id: Int): List<ReviewDTO>

    //-------------------------------------------------------------------------------------------------------------------------------------------

    @GET("requests/me")
    suspend fun getMyRequests(): List<RequestDTO>

    @POST("requests/me")
    suspend fun addRequest(@Body requestDTO: RequestDTO): RequestDTO

    @POST("requests/for_trip/{trip_id}")
    suspend fun getTripRequests(@Path("trip_id") id: Int): List<RequestDTO>

    @POST("requests/find")
    suspend fun findTrips(@Body findTripRequestDTO: FindTripRequestDTO): List<FindTripResponseDTO>

    @POST("requests/accept/{request_id}")
    suspend fun acceptRequest(@Path("request_id") id: Int): String

    @POST("requests/decline/{request_id}")
    suspend fun declineRequest(@Path("request_id") id: Int): String

    @POST("requests/finish_trip/{trip_id}")
    suspend fun finishTrip(@Path("trip_id") id: Int): String

    @POST("requests/error_trip_id/{trip_id}")
    suspend fun errorTrip(@Path("trip_id") id: Int): String

    //-------------------------------------------------------------------------------------------------------------------------------------------

    @GET("places/suggest/{to_find}")
    suspend fun suggestPlace(@Path("to_find") address: String): List<PlaceDTO>

    @GET("places/{id}")
    suspend fun getPlace(@Path("id") id: Int): PlaceDTO

    //-------------------------------------------------------------------------------------------------------------------------------------------

    @GET("pays/me")
    suspend fun getPayments(): List<PaymentDTO>

}