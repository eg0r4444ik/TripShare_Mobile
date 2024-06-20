package ru.vsu.tripshare_mobile.api.dto.cars

class CarDTO {
    val brand: String
    val model: String
    val color: String
    val year_of_manufacture: Int
    val iamges_url: String?
    val user_id: Int?
    val id: Int?

    constructor(
        brand: String,
        model: String,
        color: String,
        year_of_manufacture: Int,
        iamges_url: String?,
        user_id: Int,
        id: Int
    ) {
        this.brand = brand
        this.model = model
        this.color = color
        this.year_of_manufacture = year_of_manufacture
        this.iamges_url = iamges_url
        this.user_id = user_id
        this.id = id
    }

    constructor(
        brand: String,
        model: String,
        color: String,
        year_of_manufacture: Int,
        iamges_url: String?
    ) {
        this.brand = brand
        this.model = model
        this.color = color
        this.year_of_manufacture = year_of_manufacture
        this.iamges_url = iamges_url
        this.user_id = null
        this.id = null
    }
}