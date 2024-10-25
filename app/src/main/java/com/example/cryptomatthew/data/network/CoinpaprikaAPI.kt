package com.example.cryptomatthew.data.network

import com.example.cryptomatthew.data.network.models.Ticker
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinpaprikaAPI {
    @GET("tickers")
    fun getTickersList(@Query(value = "quotes", encoded = true) quotes: String): Call<List<Ticker>>

}