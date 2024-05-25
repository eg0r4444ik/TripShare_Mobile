package ru.vsu.tripshare_mobile.api.dto.users

class UserDTO {
    val phone: String
    val name: String
    val surname: String
    val email: String?
    val birthday: String
    val musicPreferences: String?
    val info: String?
    val talkativeness: Int?
    val attitude_towards_smoking: Int?
    val attitude_towards_animals_during_the_trip: Int?
    val id: Int
    val rating: Int?
    val avatar_id: Int?

    constructor(
        phone: String,
        name: String,
        surname: String,
        email: String?,
        birthday: String,
        musicPreferences: String?,
        info: String?,
        talkativeness: Int?,
        attitude_towards_smoking: Int?,
        attitude_towards_animals_during_the_trip: Int?,
        id: Int,
        rating: Int?,
        avatar_id: Int?
    ) {
        this.phone = phone
        this.name = name
        this.surname = surname
        this.email = email
        this.birthday = birthday
        this.musicPreferences = musicPreferences
        this.info = info
        this.talkativeness = talkativeness
        this.attitude_towards_smoking = attitude_towards_smoking
        this.attitude_towards_animals_during_the_trip = attitude_towards_animals_during_the_trip
        this.rating = rating
        this.avatar_id = avatar_id
        this.id = id
    }
}