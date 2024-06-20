package ru.vsu.tripshare_mobile.api.dto.trips

class FindTripRequestDTO {
    val start: StopDTO
    val end: StopDTO
    val comforts: ComfortsDTO
    val date: String
    val needed_seats: Int

    constructor(
        start: StopDTO,
        end: StopDTO,
        comforts: ComfortsDTO,
        date: String,
        needed_seats: Int
    ) {
        this.start = start
        this.end = end
        this.comforts = comforts
        this.date = date
        this.needed_seats = needed_seats
    }
}