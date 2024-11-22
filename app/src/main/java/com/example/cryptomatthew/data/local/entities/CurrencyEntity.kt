package com.example.cryptomatthew.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptomatthew.data.network.models.NetworkTicker

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
) {
    constructor(ticker: NetworkTicker) : this(
        ticker.id,
        ticker.rank.toInt(),
        ticker.name,
        ticker.symbol,
    )
}