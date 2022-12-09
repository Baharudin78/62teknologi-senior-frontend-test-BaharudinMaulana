package com.baharudin.enamduatest.domain.use_case

import com.baharudin.enamduatest.domain.use_case.remote.get_review.GetReviewUseCase
import com.baharudin.enamduatest.domain.use_case.remote.getbusiness.GetBusinessUseCase
import com.baharudin.enamduatest.domain.use_case.remote.getbusiness_filter.GetBusinessFilterUseCase
import com.baharudin.enamduatest.domain.use_case.remote.getbusiness_id.GetBusinessByIdUseCase
import com.baharudin.enamduatest.domain.use_case.remote.getbusiness_search.BusinessSearchStringUseCase

data class BusinessUseCase(
    val getBusiness : GetBusinessUseCase,
    val getBusinessBySearchString : BusinessSearchStringUseCase,
    val getBusinessFilter : GetBusinessFilterUseCase,
    val getBusinessById : GetBusinessByIdUseCase,
    val getBusinessReview : GetReviewUseCase
)
