package com.baharudin.enamduatest.data.remote

import com.baharudin.enamduatest.core.util.Constant.BASE_LOCATION
import com.baharudin.enamduatest.core.util.Constant.LIMIT
import com.baharudin.enamduatest.data.dto.business.BusinessesDto
import com.baharudin.enamduatest.data.dto.business_detail.BusinessDetailDto
import com.baharudin.enamduatest.data.dto.review.ReviewDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BusinessesApi {

    @GET("v3/businesses/search")
    suspend fun getSearch(
        @Query("location") location : String = BASE_LOCATION,
        @Query("limit") limit : Int = LIMIT,
    ) : BusinessesDto

    @GET("v3/businesses/search")
    suspend fun getSearchByString(
        @Query("term") term : String,
        @Query("location") location : String = BASE_LOCATION,
        @Query("limit") limit : Int = LIMIT,
    ) : BusinessesDto

    @GET("v3/businesses/search")
    suspend fun getSearchByFilter(
        @Query("sort_by") filter : String,
        @Query("location") location : String = BASE_LOCATION,
        @Query("limit") limit : Int = LIMIT,
    ) : BusinessesDto

    @GET("v3/businesses/{business_id_or_alias}")
    suspend fun getBusinessById(
        @Path("business_id_or_alias") id : String
    ) : BusinessDetailDto

    @GET("v3/businesses/{id}/review")
    suspend fun getReviewBusiness(
        @Path("id") id : String
    ) : ReviewDto
}