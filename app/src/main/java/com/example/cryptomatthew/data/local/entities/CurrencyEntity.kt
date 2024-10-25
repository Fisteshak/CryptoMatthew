package com.example.cryptomatthew.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptomatthew.data.network.models.Ticker

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
) {
    constructor(ticker: Ticker) : this(
        ticker.id,
        ticker.rank.toInt(),
        ticker.name,
        ticker.symbol,
    )
}