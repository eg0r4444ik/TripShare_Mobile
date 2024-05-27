package ru.vsu.tripshare_mobile.api.dto.cars

class CarDTO {
    val brand: String
    val model: String
    val color: String
    val year_of_manufacture: Int
    val image0_id: Int
    val image1_id: Int
    val image2_id: Int
    val image3_id: Int
    val user_id: Int?

    constructor(
        brand: String,
        model: String,
        color: String,
        year_of_manufacture: Int,
        image0_id: Int,
        image1_id: Int,
        image2_id: Int,
        image3_id: Int,
        user_id: Int
    ) {
        this.brand = brand
        this.model = model
        this.color = color
        this.year_of_manufacture = year_of_manufacture
        this.image0_id = image0_id
        this.image1_id = image1_id
        this.image2_id = image2_id
        this.image3_id = image3_id
        this.user_id = user_id
    }

    constructor(
        brand: String,
        model: String,
        color: String,
        year_of_manufacture: Int,
        image0_id: Int,
        image1_id: Int,
        image2_id: Int,
        image3_id: Int
    ) {
        this.brand = brand
        this.model = model
        this.color = color
        this.year_of_manufacture = year_of_manufacture
        this.image0_id = image0_id
        this.image1_id = image1_id
        this.image2_id = image2_id
        this.image3_id = image3_id
        this.user_id = null
    }
}