package com.baharudin.enamduatest.domain.model.review

import android.os.Parcelable
import com.baharudin.enamduatest.data.dto.review.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewModel(
    val id: String? = null,
    val rating: Int? = null,
    val text: String? = null,
    val time_created: String? = null,
    val url: String? = null,
    val user: UserModel
) : Parcelable
