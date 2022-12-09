package com.baharudin.enamduatest.domain.use_case.remote.getbusiness_id

import com.baharudin.enamduatest.core.util.Resource
import com.baharudin.enamduatest.data.dto.business_detail.toBusinessDetailModel
import com.baharudin.enamduatest.domain.model.business_detail.BusinesssDetailModel
import com.baharudin.enamduatest.domain.repository.BusinessesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBusinessByIdUseCase @Inject constructor(
    private val businessesRepository: BusinessesRepository
) {
    operator fun invoke(businessId: String): Flow<Resource<BusinesssDetailModel>> = flow {
        try {
            emit(Resource.Loading())
            val business = businessesRepository.getBusinessById(businessId).toBusinessDetailModel()
            emit(Resource.Success(business))
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?: "An unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}