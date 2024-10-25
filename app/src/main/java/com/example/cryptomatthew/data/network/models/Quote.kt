package com.example.cryptomatthew.data.network.models


data class Quote (
    val price: Double,
    val volume_24h: Double?,
    val volume_24h_change_24h: Double?,
    val market_cap: Double?,
    val market_cap_change_24h: Double?,
    val percent_change_1h: Double?,
    val percent_change_12h: Double?,
    val percent_change_24h: Double?,
    val percent_change_7d: Double?,
    val percent_change_30d: Double?,
    val percent_change_1y: Double?,
    val ath_price: Double?,
    val ath_date: String?,
    val percent_from_price_ath: Double?
)