package com.baharudin.enamduatest.data.dto.review

data class ReviewDto(
    val possible_languages: List<String>,
    val reviews: List<Review>,
    val total: Int
)