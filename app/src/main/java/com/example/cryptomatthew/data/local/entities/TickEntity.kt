package com.example.cryptomatthew.data.local.entities

import androidx.room.Entity

@Entity(tableName = "tick", primaryKeys = ["currencyId", "timestamp"])
data class TickEntity(
    val currencyId: String,
    val timestamp: String,
    val price: Double,
    val volume24h: Double,
    val marketCap: Double,
)
