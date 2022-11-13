package com.currency.exchanger.data.currency

import com.currency.exchanger.core.cloud.ApiService
import com.currency.exchanger.data.currency.cloud.CurrencyCloudDataSource
import com.currency.exchanger.data.currency.cloud.CurrencyCloudToDataMapper
import com.currency.exchanger.domain.currency.CurrencyRepository
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val cloudToDataMapper: CurrencyCloudToDataMapper,
) : CurrencyRepository {

    override suspend fun getAllCurrency(query: String?): Flow<CurrencyResponse> = flow {
        emit(CurrencyResponse.Loading)
        val response = CurrencyCloudDataSource(client, ApiService.token).fetchCloud(query)
        emit(CurrencyResponse.Success(cloudToDataMapper.map(response)))
    }.catch { emit(CurrencyResponse.Failure(it)) }

}