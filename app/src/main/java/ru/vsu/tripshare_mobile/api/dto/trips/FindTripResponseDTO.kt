package ru.vsu.tripshare_mobile.api.dto.trips

class FindTripResponseDTO {
    val start_distance: Int
    val start: StopDTO
    val end_distance: Int
    val end: StopDTO
    val trip: TripDTO
    val cost: Int

    constructor(
        start_distance: Int,
        start: StopDTO,
        end_distance: Int,
        end: StopDTO,
        trip: TripDTO,
        cost: Int
    ) {
        this.start_distance = start_distance
        this.start = start
        this.end_distance = end_distance
        this.end = end
        this.trip = trip
        this.cost = cost
    }
}