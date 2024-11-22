package com.example.cryptomatthew.data.network

import com.example.cryptomatthew.data.network.models.NetworkTick
import com.example.cryptomatthew.data.network.models.NetworkTicker
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinpaprikaAPI {
    @GET("tickers")
    fun getTickersList(@Query(value = "quotes", encoded = true) quotes: String): Call<List<NetworkTicker>>

    @GET("tickers")
    suspend fun getTickersListSync(@Query(value = "quotes", encoded = true) quotes: String): Response<List<NetworkTicker>>

    @GET("tickers/{ticker_id}/historical")
    suspend fun getTickerHistory(
        @Path("ticker_id") tickerId: String,
        @Query("start") startDate: String,
        @Query("interval") interval: String,
        ): Response<List<NetworkTick>>
}