package com.baharudin.enamduatest.domain.model.business_detail

import android.os.Parcelable
import com.baharudin.enamduatest.data.dto.business_detail.*
import kotlinx.parcelize.Parcelize


@Parcelize
data class BusinesssDetailModel(
    val alias: String? = null,
    val coordinates: Coordinates? = null,
    val display_phone: String? = null,
    val id: String? = null,
    val image_url: String? = null,
    val messaging: Messaging? = null,
    val name: String? = null,
    val phone: String? = null,
    val photos: List<String?>,
    val price: String? = null,
    val rating: Double? = null,
    val review_count: Int? = null,
    val url: String? = null
) : Parcelable
