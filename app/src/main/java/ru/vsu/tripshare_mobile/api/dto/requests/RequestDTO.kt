package ru.vsu.tripshare_mobile.api.dto.requests

import ru.vsu.tripshare_mobile.api.dto.trips.TripDTO

class RequestDTO {
    val number_of_seats: Int
    val departure_id: Int
    val arrival_id: Int
    val trip_id: Int
    val cost: Int?
    val user_id: Int?
    val id: Int?
    val request_datetime: String?
    val status: RequestStatusDTO?
    val status_change_datetime: String?
    val trip: TripDTO?

    constructor(
        number_of_seats: Int,
        departure_id: Int,
        arrival_id: Int,
        trip_id: Int,
        cost: Int,
        user_id: Int,
        id: Int,
        request_datetime: String,
        status: RequestStatusDTO,
        status_change_datetime: String,
        trip: TripDTO
    ) {
        this.number_of_seats = number_of_seats
        this.departure_id = departure_id
        this.arrival_id = arrival_id
        this.trip_id = trip_id
        this.cost = cost
        this.user_id = user_id
        this.id = id
        this.request_datetime = request_datetime
        this.status = status
        this.status_change_datetime = status_change_datetime
        this.trip = trip
    }

    constructor(
        number_of_seats: Int,
        departure_id: Int,
        arrival_id: Int,
        trip_id: Int,
        cost: Int,
        user_id: Int,
        id: Int,
        request_datetime: String,
        status: RequestStatusDTO,
        status_change_datetime: String,
    ) {
        this.number_of_seats = number_of_seats
        this.departure_id = departure_id
        this.arrival_id = arrival_id
        this.trip_id = trip_id
        this.cost = cost
        this.user_id = user_id
        this.id = id
        this.request_datetime = request_datetime
        this.status = status
        this.status_change_datetime = status_change_datetime
        this.trip = null
    }

    constructor(
        number_of_seats: Int,
        departure_id: Int,
        arrival_id: Int,
        trip_id: Int,
    ) {
        this.number_of_seats = number_of_seats
        this.departure_id = departure_id
        this.arrival_id = arrival_id
        this.trip_id = trip_id
        this.cost = null
        this.user_id = null
        this.id = null
        this.request_datetime = null
        this.status = null
        this.status_change_datetime = null
        this.trip = null
    }
}