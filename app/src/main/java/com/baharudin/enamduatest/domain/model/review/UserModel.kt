package com.baharudin.enamduatest.domain.model.review

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: String? = null,
    val image_url: String? = null,
    val name: String? = null,
    val profile_url: String? = null
) : Parcelable
