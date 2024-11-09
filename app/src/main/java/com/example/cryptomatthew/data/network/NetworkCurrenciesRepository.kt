package com.example.cryptomatthew.data.network

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class NetworkCurrenciesRepository @Inject constructor(
    private val currencyAPI: CoinpaprikaAPI,
    private val refreshIntervalMs: Long = 5000 ,
) {


    private val quotes = listOf("USD", "RUB").joinToString(separator = ",")

    val latestTickers = flow {

        while (true) {

            val latestTickers = currencyAPI.getTickersListSync(quotes)
            Log.d("TICKERS EMITTER FLOW", "got data")
            emit(latestTickers)
            Log.d("TICKERS EMITTER FLOW", "emitted, now waiting 5s")
            delay(refreshIntervalMs)
        }
    }

}
