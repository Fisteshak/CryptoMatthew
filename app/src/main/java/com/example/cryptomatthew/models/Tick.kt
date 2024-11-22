package com.example.cryptomatthew.models

import com.example.cryptomatthew.data.local.entities.TickEntity

data class Tick(
    val price: Double,
    val timestamp: String,
    val volume24h: Double,
    val marketCap: Double,
) {
    constructor(tickEntity: TickEntity) : this(
        tickEntity.price,
        tickEntity.timestamp,
        tickEntity.volume24h,
        tickEntity.marketCap
    )
}

