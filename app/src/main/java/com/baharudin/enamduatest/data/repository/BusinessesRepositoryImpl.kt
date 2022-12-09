package com.baharudin.enamduatest.data.repository

import com.baharudin.enamduatest.data.dto.business.BusinessesDto
import com.baharudin.enamduatest.data.dto.business_detail.BusinessDetailDto
import com.baharudin.enamduatest.data.dto.review.ReviewDto
import com.baharudin.enamduatest.data.remote.BusinessesApi
import com.baharudin.enamduatest.domain.repository.BusinessesRepository
import javax.inject.Inject

class BusinessesRepositoryImpl @Inject constructor(
    private val businessApi : BusinessesApi
) : BusinessesRepository {
    override suspend fun getBusiness(): BusinessesDto {
        return businessApi.getSearch()
    }

    override suspend fun getBusinessBySearch(search: String): BusinessesDto {
        return businessApi.getSearchByString(search)
    }

    override suspend fun getBusinessByFilter(filter: String): BusinessesDto {
        return businessApi.getSearchByFilter(filter)
    }

    override suspend fun getBusinessById(id: String): BusinessDetailDto {
        return businessApi.getBusinessById(id)
    }

    override suspend fun getBusinessReview(id: String): ReviewDto {
        return businessApi.getReviewBusiness(id)
    }
}