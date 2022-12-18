package com.baharudin.enamduatest.data.dto.review

import com.baharudin.enamduatest.domain.model.review.UserModel

data class User(
    val id: String? = null,
    val image_url: String? = null,
    val name: String? = null,
    val profile_url: String? = null
)

fun User.toUserModel() : UserModel{
    return UserModel(
        id, image_url, name, profile_url
    )
}