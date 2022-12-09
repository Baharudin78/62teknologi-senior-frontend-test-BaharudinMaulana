package com.baharudin.enamduatest.domain.use_case.remote.get_review

import com.baharudin.enamduatest.core.util.Resource
import com.baharudin.enamduatest.data.dto.business_detail.toBusinessDetailModel
import com.baharudin.enamduatest.data.dto.review.toReviewModel
import com.baharudin.enamduatest.domain.model.review.ReviewModel
import com.baharudin.enamduatest.domain.repository.BusinessesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetReviewUseCase @Inject constructor(
    private val businessesRepository: BusinessesRepository
) {
    operator fun invoke(businessId : String) : Flow<Resource<List<ReviewModel>>> = flow {
        try {
            emit(Resource.Loading())
            val business = businessesRepository.getBusinessReview(businessId).reviews.map { it.toReviewModel()}
            emit(Resource.Success(business))
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?: "An unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}