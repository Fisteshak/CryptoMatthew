package com.example.cryptomatthew.data.network.models


data class NetworkTicker(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Double,
    val circulating_supply: Double?,
    val total_supply: Double?,
    val max_supply: Double?,
    val beta_value: Double?,
    val first_data_at: String?,
    val last_updated: String?,
    val quotes: NetworkQuotes
)


