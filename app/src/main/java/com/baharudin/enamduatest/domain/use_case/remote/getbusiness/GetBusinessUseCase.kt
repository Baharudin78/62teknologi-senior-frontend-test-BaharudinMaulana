package com.baharudin.enamduatest.domain.use_case.remote.getbusiness

import com.baharudin.enamduatest.core.util.Resource
import com.baharudin.enamduatest.data.dto.business.toBusinesseModel
import com.baharudin.enamduatest.domain.model.business.BusinessesModel
import com.baharudin.enamduatest.domain.repository.BusinessesRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBusinessUseCase @Inject constructor(
    private val businessesRepository: BusinessesRepository
) {
    operator fun invoke() : Flow<Resource<List<BusinessesModel>>> = callbackFlow {
        try {
            this.trySend(Resource.Loading())
            val business = businessesRepository.getBusiness().businesses.map { it.toBusinesseModel() }
            this.trySend(Resource.Success(business))
        }catch (e : HttpException) {
            this.trySend(Resource.Error(e.localizedMessage?: "Unexpected Error"))
        }catch (e : IOException) {
            this.trySend(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
        awaitClose { this.cancel() }
    }
}