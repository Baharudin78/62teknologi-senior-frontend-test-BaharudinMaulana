package com.baharudin.enamduatest.data.dto.business

import com.baharudin.enamduatest.domain.model.business.BusinessesModel

data class Businesse(
    val alias: String? = null,
    val categories: List<Category?>,
    val coordinates: Coordinates?,
    val display_phone: String? = null,
   // val distance: Double? = null,
    val id: String? = null,
    val image_url: String? = null,
    val is_closed: Boolean? = null,
    val location: Location? = null,
    val name: String? = null,
    val phone: String? = null,
    val price: String? = null,
    val rating: Double,
    val review_count: Int,
    val transactions: List<String?>,
    val url: String? = null
)

fun Businesse.toBusinesseModel() : BusinessesModel {
    return BusinessesModel(
        alias = alias.orEmpty() ,
        displayPhone = display_phone.orEmpty(),
       // distance = distance,
        id = id.orEmpty(),
        imageUrl = image_url.orEmpty(),
        name = name.orEmpty(),
        phone = phone.orEmpty(),
        price = price.orEmpty(),
        rating = rating,
        reviewCount = review_count,
        url = url.orEmpty()
    )
}