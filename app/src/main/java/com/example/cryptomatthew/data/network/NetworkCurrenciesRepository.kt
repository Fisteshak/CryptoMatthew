package com.example.cryptomatthew.data.network

import android.util.Log
import com.example.cryptomatthew.data.network.models.NetworkTick
import com.example.cryptomatthew.data.network.models.NetworkTicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject


class NetworkCurrenciesRepository @Inject constructor(
    private val currencyAPI: CoinpaprikaAPI,
    private val refreshIntervalMs: Long = 60000 ,
) {


    private val quotes = listOf("USD", "RUB").joinToString(separator = ",")

    fun getLatestTickers(terminateAfter: Int? = null): Flow<Response<List<NetworkTicker>>> {
        var i = terminateAfter
        if (i != null) assert(i > 0)
        return flow {
            while (true) {
                Log.d("NetworkCurrenciesRepository", "getting tickers")
                try {
                    val latestTickers = currencyAPI.getTickersListSync(quotes)

                    Log.d("NetworkCurrenciesRepository", "got response: ${latestTickers.code()}")
                    emit(latestTickers)
                } catch (e: UnknownHostException) {
                    Log.d("NetworkCurrenciesRepository", "Exception: $e")
                } finally {
                    if (i != null) {
                        i--
                        if (i == 0) break
                    }
                    delay(refreshIntervalMs)
                }
            }
        }
    }


    // gets ticker history with 7d interval. Start date is a string with format 'yyyy-mm-dd'
    suspend fun getWeeklyTickerHistory(tickerId: String, startDate: String): Response<List<NetworkTick>>? {
        try {
            return currencyAPI.getTickerHistory(
                tickerId = tickerId,
                startDate = startDate,
                interval = "1d"
            )
        } catch (e: Exception) {
            Log.d("EXCEPTION", e.toString())
            return null;
        }
    }

}
