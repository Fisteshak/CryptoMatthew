package com.example.cryptomatthew.data.network

import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class NetworkCurrenciesRepository @Inject constructor(
    private val currencyAPI: CoinpaprikaAPI,
    private val refreshIntervalMs: Long = 5000
) {


    private val quotes = listOf("USD", "RUB").joinToString(separator = ",")

    val latestTickers = flow {
        val latestTickers = currencyAPI.getTickersListSync(quotes)
        emit(latestTickers)
        kotlinx.coroutines.delay(refreshIntervalMs)
    }

}
