package com.baharudin.enamduatest.data.dto.review

import com.baharudin.enamduatest.domain.model.review.ReviewModel

data class Review(
    val id: String? = null,
    val rating: Int? = null,
    val text: String? = null,
    val time_created: String? = null,
    val url: String? = null,
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