package com.currency.exchanger.data.currency.cloud

import com.currency.exchanger.data.currency.CurrencyResponseData

class CurrencyCloudToDataMapper {

    fun map(cloud: CurrencyResponseCloud) = CurrencyResponseData(
        cloud.success,
        cloud.time,
        cloud.base,
        cloud.date,
        cloud.rates
    )
}