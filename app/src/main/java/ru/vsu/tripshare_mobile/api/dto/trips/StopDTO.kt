package ru.vsu.tripshare_mobile.api.dto.trips

import java.util.Date

class StopDTO {
    val place: String
    val place_name: String
    val datetime: Date
    val num: Int
    val is_start: Boolean?
    val is_stop: Boolean?
    val trip_id: Int?
    val id: Int?

    constructor(
        place: String,
        place_name: String,
        datetime: Date,
        num: Int,
        is_start: Boolean?,
        is_stop: Boolean?,
        trip_id: Int?,
        id: Int?
    ) {
        this.place = place
        this.place_name = place_name
        this.datetime = datetime
        this.num = num
        this.is_start = is_start
        this.is_stop = is_stop
        this.trip_id = trip_id
        this.id = id
    }
}