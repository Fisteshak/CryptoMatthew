package com.example.cryptomatthew.data.network.models

data class NetworkTick(
    val price: Double,
    val timestamp: String,
    val volume24h: Double,
    val marketCap: Double,
)