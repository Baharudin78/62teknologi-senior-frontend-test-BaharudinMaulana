package com.baharudin.enamduatest.domain.model.review

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: String,
    val image_url: String,
    val name: String,
    val profile_url: String
) : Parcelable
