package com.baharudin.enamduatest.data.dto.review

import com.baharudin.enamduatest.domain.model.review.ReviewModel

data class Review(
    val id: String,
    val rating: Int,
    val text: String,
    val time_created: String,
    val url: String,
    val user: User
)

fun Review.toReviewModel() : ReviewModel {
    return ReviewModel(
        id,
        rating,
        text,
        time_created,
        url,
        user.toUserModel()
    )
}