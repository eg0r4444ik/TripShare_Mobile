package ru.vsu.tripshare_mobile.api.dto.trips

class ComfortsDTO {
    val max_two_passengers_in_the_back_seat: Boolean
    val smoking_allowed: Boolean
    val pets_allowed: Boolean
    val free_trunk: Boolean

    constructor(
        max_two_passengers_in_the_back_seat: Boolean,
        smoking_allowed: Boolean,
        pets_allowed: Boolean,
        free_trunk: Boolean
    ) {
        this.max_two_passengers_in_the_back_seat = max_two_passengers_in_the_back_seat
        this.smoking_allowed = smoking_allowed
        this.pets_allowed = pets_allowed
        this.free_trunk = free_trunk
    }
}