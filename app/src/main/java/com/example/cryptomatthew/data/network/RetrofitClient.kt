package com.example.cryptomatthew.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val baseURL = "https://api.coinpaprika.com/v1/"

    val instance: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    val apiService: CoinpaprikaAPI = RetrofitClient.instance.create(CoinpaprikaAPI::class.java)

}