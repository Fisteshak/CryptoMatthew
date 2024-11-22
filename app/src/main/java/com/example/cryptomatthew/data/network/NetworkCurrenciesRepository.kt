package com.example.cryptomatthew.data.network

import android.util.Log
import com.example.cryptomatthew.data.network.models.NetworkTick
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


class NetworkCurrenciesRepository @Inject constructor(
    private val currencyAPI: CoinpaprikaAPI,
    private val refreshIntervalMs: Long = 60000 ,
) {


    private val quotes = listOf("USD", "RUB").joinToString(separator = ",")

    val latestTickers = flow {
        while (true) {
            Log.d("TICKERS EMITTER FLOW", "getting tickers")
            try {
                val latestTickers = currencyAPI.getTickersListSync(quotes)

                Log.d("TICKERS EMITTER FLOW", "got response: ${latestTickers.code()}")
                emit(latestTickers)
                Log.d("TICKERS EMITTER FLOW", "emitted, data now waiting 1min")
            } catch (e: Exception) {
                Log.d("EXCEPTION", e.message.toString())
            } finally {
                delay(refreshIntervalMs)
            }
        }
    }

    suspend fun getTickerHistory(tickerId: String): Response<List<NetworkTick>>? {
        try {
            return currencyAPI.getTickerHistory(tickerId = tickerId, startDate = "2024-01-01", interval = "7d")
        } catch (e: Exception) {
            Log.d("EXCEPTION", e.message.toString())
            return null;
        }
    }

}
