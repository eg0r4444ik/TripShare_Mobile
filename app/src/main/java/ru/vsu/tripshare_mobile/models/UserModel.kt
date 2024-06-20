package ru.vsu.tripshare_mobile.models

class UserModel {
    var id: Int
    var name: String
    var surname: String
    var phone: String
    var email: String?
    var birthday: String
    var rating: Int?
    var avatarUrl: String?
    var musicPreferences: String?
    var talkativeness: Int?
    var attitudeTowardsSmoking: Int?
    var attitudeTowardsAnimals: Int?
    var info: String?

    constructor(
        id: Int,
        name: String,
        surname: String,
        phone: String,
        email: String?,
        birthday: String,
        rating: Int?,
        avatarUrl: String?,
        musicPreferences: String?,
        talkativeness: Int?,
        attitudeTowardsSmoking: Int?,
        attitudeTowardsAnimals: Int?,
        info: String?
    ) {
        this.id = id
        this.name = name
        this.surname = surname
        this.phone = phone
        this.email = email
        this.birthday = birthday
        this.rating = rating
        this.avatarUrl = avatarUrl
        this.musicPreferences = musicPreferences
        this.talkativeness = talkativeness
        this.attitudeTowardsSmoking = attitudeTowardsSmoking
        this.attitudeTowardsAnimals = attitudeTowardsAnimals
        this.info = info
    }
}
