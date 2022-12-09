package com.baharudin.enamduatest.core.di

import com.baharudin.enamduatest.core.util.Constant.BASE_URL
import com.baharudin.enamduatest.core.util.Constant.TOKEN_ACCESS
import com.baharudin.enamduatest.core.util.Constant.TOKEN_TYPE
import com.baharudin.enamduatest.core.util.TokenInterceptor
import com.baharudin.enamduatest.data.remote.BusinessesApi
import com.baharudin.enamduatest.data.repository.BusinessesRepositoryImpl
import com.baharudin.enamduatest.domain.repository.BusinessesRepository
import com.baharudin.enamduatest.domain.use_case.BusinessUseCase
import com.baharudin.enamduatest.domain.use_case.remote.get_review.GetReviewUseCase
import com.baharudin.enamduatest.domain.use_case.remote.getbusiness.GetBusinessUseCase
import com.baharudin.enamduatest.domain.use_case.remote.getbusiness_filter.GetBusinessFilterUseCase
import com.baharudin.enamduatest.domain.use_case.remote.getbusiness_id.GetBusinessByIdUseCase
import com.baharudin.enamduatest.domain.use_case.remote.getbusiness_search.BusinessSearchStringUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshiConverter() : Moshi = Moshi
        .Builder()
        .run {
            add(KotlinJsonAdapterFactory())
                .build()
        }

    @Provides
    @Singleton
    fun provideApiService(moshi: Moshi, okHttpClient: OkHttpClient) : BusinessesApi =
        Retrofit
            .Builder()
            .run {
                baseUrl(BASE_URL)
                addConverterFactory(MoshiConverterFactory.create(moshi))
                client(okHttpClient)
                build()
            }.create(BusinessesApi::class.java)

    @Provides
    @Singleton
    fun provideHttpInterceptor() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(
            TokenInterceptor(
                TOKEN_ACCESS,
                TOKEN_TYPE
            )
        )
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(
        businessApi : BusinessesApi
    ): BusinessesRepository{
        return BusinessesRepositoryImpl(businessApi)
    }

    @Provides
    @Singleton
    fun provideUseCase(
        repository: BusinessesRepository
    ) : BusinessUseCase{
        return BusinessUseCase(
            getBusiness = GetBusinessUseCase(repository),
            getBusinessFilter = GetBusinessFilterUseCase(repository),
            getBusinessBySearchString = BusinessSearchStringUseCase(repository),
            getBusinessById = GetBusinessByIdUseCase(repository),
            getBusinessReview = GetReviewUseCase(repository)
        )
    }
}