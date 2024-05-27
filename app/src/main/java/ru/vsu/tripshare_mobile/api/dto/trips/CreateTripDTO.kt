package ru.vsu.tripshare_mobile.api.dto.trips

class CreateTripDTO {
    val max_passengers: Int
    val cost_sum: Int
    val max_two_passengers_in_the_back_seat: Boolean
    val smoking_allowed: Boolean
    val e_cigarettes_allowed: Boolean
    val pets_allowed: Boolean
    val free_trunk: Boolean
    val car_id: Int
    val stops: List<StopDTO>

    constructor(
        max_passengers: Int,
        cost_sum: Int,
        max_two_passengers_in_the_back_seat: Boolean,
        smoking_allowed: Boolean,
        e_cigarettes_allowed: Boolean,
        pets_allowed: Boolean,
        free_trunk: Boolean,
        car_id: Int,
        stops: List<StopDTO>
    ) {
        this.max_passengers = max_passengers
        this.cost_sum = cost_sum
        this.max_two_passengers_in_the_back_seat = max_two_passengers_in_the_back_seat
        this.smoking_allowed = smoking_allowed
        this.e_cigarettes_allowed = e_cigarettes_allowed
        this.pets_allowed = pets_allowed
        this.free_trunk = free_trunk
        this.car_id = car_id
        this.stops = stops
    }
}