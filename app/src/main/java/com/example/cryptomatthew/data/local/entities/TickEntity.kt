package com.example.cryptomatthew.data.local.entities

import androidx.room.Entity
import com.example.cryptomatthew.data.network.models.NetworkTick

@Entity(tableName = "tick", primaryKeys = ["currencyId", "timestamp"])
data class TickEntity(
    val currencyId: String,
    val timestamp: String,
    val price: Double,
    val volume24h: Double,
    val marketCap: Double,
) {
    constructor(currencyId: String, networkTick: NetworkTick) : this(
        currencyId,
        networkTick.timestamp,
        networkTick.price,
        networkTick.volume24h,
        networkTick.marketCap
    )
}
