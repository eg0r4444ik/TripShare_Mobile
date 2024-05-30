package ru.vsu.tripshare_mobile.api.dto.reviews

import ru.vsu.tripshare_mobile.api.dto.users.UserDTO

class ReviewDTO {
    val text: String
    val rating: Int
    val image_id: Int?
    val user_id: Int
    val writer: UserDTO?
    val id: Int?

    constructor(text: String, rating: Int, image_id: Int?, user_id: Int, writer: UserDTO, id: Int) {
        this.text = text
        this.rating = rating
        this.image_id = image_id
        this.user_id = user_id
        this.writer = writer
        this.id = id
    }

    constructor(text: String, rating: Int, image_id: Int?, user_id: Int) {
        this.text = text
        this.rating = rating
        this.image_id = image_id
        this.user_id = user_id
        this.writer = null
        this.id = null
    }
}