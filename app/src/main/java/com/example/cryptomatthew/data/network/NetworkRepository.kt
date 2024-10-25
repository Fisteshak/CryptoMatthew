package com.example.cryptomatthew.data.network

import com.example.cryptomatthew.data.network.models.Tickers
import com.example.cryptomatthew.models.Currencies
import com.example.cryptomatthew.models.Currency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NetworkRepository {

    private val apiService = RetrofitClient.apiService
    private val quotes = listOf("USD", "RUB").joinToString(separator = ",")

    fun fetchTickersList(
        onResponse: (code: Int, currencies: Currencies?) -> Unit,
        onFailure: (t: Throwable) -> Unit
    ) {
        val call: Call<Tickers> = apiService.getTickersList(quotes)
        call.enqueue(object : Callback<Tickers> {
            override fun onFailure(call: Call<Tickers>, t: Throwable) {
                onFailure(t)
            }

            override fun onResponse(call: Call<Tickers>, response: Response<Tickers>) {
                val tickers = response.body()
                val currencies = tickers?.map { Currency(it) }

                onResponse(response.code(), currencies)
            }

        })
    }

}
