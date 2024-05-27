package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.reviews.ReviewDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.ReviewModel

object ReviewService {

    fun addReview(reviewModel: ReviewModel){
        try {
            val reviewDTO = fromModelToDTO(reviewModel)
            AppConfig.retrofitAPI.addReview(reviewDTO)
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    suspend fun getReview(reviewId: Int): Result<ReviewModel>{
        return withContext(Dispatchers.IO) {
            try {
                val reviewDTO = AppConfig.retrofitAPI.getReview(reviewId)
                val review = fromDTOtoModel(reviewDTO)
                Result.success(review)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    fun deleteReview(reviewId: Int){
        try {
            AppConfig.retrofitAPI.deleteReview(reviewId)
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun fromModelToDTO(reviewModel: ReviewModel): ReviewDTO {
        val reviewDTO = ReviewDTO(
            reviewModel.comment,
            reviewModel.grade,
            1,
            reviewModel.authorId
        )
        return reviewDTO
    }

    private fun fromDTOtoModel(reviewDTO: ReviewDTO): ReviewModel {
        val reviewModel = ReviewModel(
            reviewDTO.user_id,
            reviewDTO.rating,
            reviewDTO.text
        )
        return reviewModel
    }

}