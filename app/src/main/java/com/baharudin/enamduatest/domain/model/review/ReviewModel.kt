package com.baharudin.enamduatest.domain.model.review

import android.os.Parcelable
import com.baharudin.enamduatest.data.dto.review.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewModel(
    val id: String,
    val rating: Int,
    val text: String,
    val time_created: String,
    val url: String,
    val user: UserModel
) : Parcelable
