package com.baharudin.enamduatest.domain.use_case.remote.getbusiness_filter

import com.baharudin.enamduatest.core.util.Resource
import com.baharudin.enamduatest.data.dto.business.toBusinesseModel
import com.baharudin.enamduatest.domain.model.business.BusinessesModel
import com.baharudin.enamduatest.domain.repository.BusinessesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetBusinessFilterUseCase @Inject constructor(
    private val businessesRepository: BusinessesRepository
) {
    operator fun invoke(filter : String) : Flow<Resource<List<BusinessesModel>>> = flow {
        try {
            emit(Resource.Loading())
            val businessFilter = businessesRepository.getBusinessByFilter(filter).businesses.map { it.toBusinesseModel() }
            emit(Resource.Success(businessFilter))
        }catch (e : HttpException) {
            emit(Resource.Error(e.localizedMessage?: "An unexpected error"))
        }catch (e : IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}