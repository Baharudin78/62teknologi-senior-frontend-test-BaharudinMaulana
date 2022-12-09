package com.baharudin.enamduatest.data.dto.business_detail

import com.baharudin.enamduatest.domain.model.business_detail.BusinesssDetailModel

data class BusinessDetailDto(
    val alias: String,
    val categories: List<Category>,
    val coordinates: Coordinates,
    val display_phone: String,
    val hours: List<Hour>,
    val id: String,
    val image_url: String,
    val is_claimed: Boolean,
    val is_closed: Boolean,
    val location: Location,
    val messaging: Messaging,
    val name: String,
    val phone: String,
    val photos: List<String>,
    val price: String,
    val rating: Double,
    val review_count: Int,
    val transactions: List<Any>,
    val url: String
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