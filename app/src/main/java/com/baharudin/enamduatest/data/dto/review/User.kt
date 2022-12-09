package com.baharudin.enamduatest.data.dto.review

import com.baharudin.enamduatest.domain.model.review.UserModel

data class User(
    val id: String,
    val image_url: String,
    val name: String,
    val profile_url: String
)

fun User.toUserModel() : UserModel{
    return UserModel(
        id, image_url, name, profile_url
    )
}