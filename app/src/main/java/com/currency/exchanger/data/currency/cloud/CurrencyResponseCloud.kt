package com.currency.exchanger.data.currency.cloud

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponseCloud(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("timestamp") val time: Long? = null,
    @SerializedName("base") val base: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("rates") val rates: Map<String, Double>?,
)