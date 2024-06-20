package ru.vsu.tripshare_mobile.models

import ru.vsu.tripshare_mobile.api.dto.requests.RequestStatusDTO
import ru.vsu.tripshare_mobile.api.dto.trips.TripDTO

data class RequestModel(
    val number_of_seats: Int,
    val departure_id: Int,
    val arrival_id: Int,
    val trip_id: Int,
    val cost: Int?,
    val user: UserModel?,
    val id: Int?,
    val request_datetime: String?,
    val status: RequestStatusDTO?,
    val status_change_datetime: String?,
    val trip: TripDTO?
)
