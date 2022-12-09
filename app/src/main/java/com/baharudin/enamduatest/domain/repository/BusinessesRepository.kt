package com.baharudin.enamduatest.domain.repository

import com.baharudin.enamduatest.data.dto.business.BusinessesDto
import com.baharudin.enamduatest.data.dto.business_detail.BusinessDetailDto
import com.baharudin.enamduatest.data.dto.review.ReviewDto

interface BusinessesRepository {
    suspend fun getBusiness() : BusinessesDto
    suspend fun getBusinessBySearch(search : String) : BusinessesDto
    suspend fun getBusinessByFilter(filter : String) : BusinessesDto
    suspend fun getBusinessById(id : String) : BusinessDetailDto
    suspend fun getBusinessReview(id : String) : ReviewDto
}