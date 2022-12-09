package com.baharudin.enamduatest.domain.model.business

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusinessesModel(
    val alias: String,
    val displayPhone: String,
   // val distance: Double,
    val id: String,
    val imageUrl: String,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Double,
    val reviewCount: Int,
    val url: String
) : Parcelable
