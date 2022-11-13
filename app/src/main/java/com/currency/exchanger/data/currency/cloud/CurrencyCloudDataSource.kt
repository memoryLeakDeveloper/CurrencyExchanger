package com.currency.exchanger.data.currency.cloud

import com.currency.exchanger.core.cloud.ApiService
import io.ktor.client.*
import io.ktor.client.request.*

class CurrencyCloudDataSource(private val client: HttpClient, private val token: String) {
    suspend fun fetchCloud(query: String?): CurrencyResponseCloud {
        return try {
            val base = query?.let { "&base=$query" } ?: run { "" }
            client.get {
                url("${ApiService.url}latest?access_key=$token${base}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}