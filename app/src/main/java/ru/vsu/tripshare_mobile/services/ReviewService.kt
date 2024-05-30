package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import retrofit2.http.Path
import ru.vsu.tripshare_mobile.api.dto.reviews.ReviewDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.ReviewModel

object ReviewService {

    fun addReview(reviewModel: ReviewModel, userId: Int){
        try {
            val reviewDTO = fromModelToDTO(reviewModel, userId)
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

    suspend fun getMyReviews(): Result<List<ReviewModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val reviewsDTO = AppConfig.retrofitAPI.getMyReviews()
                val reviews = mutableListOf<ReviewModel>()
                reviewsDTO.forEach{
                    reviews.add(fromDTOtoModel(it))
                }
                Result.success(reviews)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getUsersReviews(userId: Int): Result<List<ReviewModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val reviewsDTO = AppConfig.retrofitAPI.getUsersReviews(userId)
                val reviews = mutableListOf<ReviewModel>()
                reviewsDTO.forEach{
                    reviews.add(fromDTOtoModel(it))
                }
                Result.success(reviews)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun fromModelToDTO(reviewModel: ReviewModel, userId: Int): ReviewDTO {
        val reviewDTO = ReviewDTO(
            reviewModel.comment,
            reviewModel.grade,
            null,
            userId
        )
        return reviewDTO
    }

    private fun fromDTOtoModel(reviewDTO: ReviewDTO): ReviewModel {
        val reviewModel = ReviewModel(
            UserService.fromDTOtoModel(reviewDTO.writer!!),
            reviewDTO.rating,
            reviewDTO.text
        )
        return reviewModel
    }

}