package com.baharudin.enamduatest.data.dto.business_detail

import com.baharudin.enamduatest.domain.model.business_detail.BusinesssDetailModel

data class BusinessDetailDto(
    val alias: String? = null,
    val coordinates: Coordinates? = null,
    val display_phone: String? = null,
    val id: String? = null,
    val image_url: String? = null,
    val messaging: Messaging?,
    val name: String? = null,
    val phone: String? = null,
    val photos: List<String?>,
    val price: String? = null,
    val rating: Double? = null,
    val review_count: Int? = null,
    val url: String? = null
)

fun BusinessDetailDto.toBusinessDetailModel(): BusinesssDetailModel{
    return BusinesssDetailModel(
        alias,
        coordinates,
        display_phone,
        id,
        image_url,
        messaging,
        name,
        phone,
        photos,
        price,
        rating,
        review_count,
        url
    )
}