package ru.vsu.tripshare_mobile.api.dto.places

class PlaceDTO {
    val region: String
    val municipality: String
    val settlement: String
    val type: String
    val address: String
    val population: Int
    val latitude_dd: Double
    val longitude_dd: Double
    val id: Int

    constructor(
        region: String,
        municipality: String,
        settlement: String,
        type: String,
        address: String,
        population: Int,
        latitude_dd: Double,
        longitude_dd: Double,
        id: Int
    ) {
        this.region = region
        this.municipality = municipality
        this.settlement = settlement
        this.type = type
        this.address = address
        this.population = population
        this.latitude_dd = latitude_dd
        this.longitude_dd = longitude_dd
        this.id = id
    }
}