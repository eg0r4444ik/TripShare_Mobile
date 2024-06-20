package ru.vsu.tripshare_mobile.models

data class ReviewModel(
    val author: UserModel,
    val grade: Int,
    val comment: String
)
