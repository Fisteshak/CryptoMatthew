package com.example.cryptomatthew.data.network

import android.util.Log
import com.example.cryptomatthew.data.network.models.Ticker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class NetworkCurrenciesRepository @Inject constructor(private val apiService: CoinpaprikaAPI) {

    private val quotes = listOf("USD", "RUB").joinToString(separator = ",")

    fun fetchTickers(
        onResponse: (code: Int, tickers: List<Ticker>?) -> Unit,
        onFailure: (t: Throwable) -> Unit
    ) {
        val call: Call<List<Ticker>> = apiService.getTickersList(quotes)
        call.enqueue(object : Callback<List<Ticker>> {
            override fun onFailure(call: Call<List<Ticker>>, t: Throwable) {
                onFailure(t)
            }

            override fun onResponse(call: Call<List<Ticker>>, response: Response<List<Ticker>>) {
                val tickers = response.body()
                Log.d("Network", "onResponse: $tickers")
                onResponse(response.code(), tickers)
            }

        })
    }

}
