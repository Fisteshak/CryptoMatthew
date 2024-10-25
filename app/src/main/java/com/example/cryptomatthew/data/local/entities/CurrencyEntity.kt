package com.example.cryptomatthew.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptomatthew.data.network.models.Ticker
import com.example.cryptomatthew.models.Financials
import com.example.cryptomatthew.models.Tick

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val finsUSD: Financials?,
    val finsRUB: Financials?,
    val history: List<Tick>? = null,
) {
    constructor(ticker: Ticker) : this(
        ticker.id,
        ticker.rank.toInt(),
        ticker.name,
        ticker.symbol,
        Financials(ticker.quotes.USD),
        Financials(ticker.quotes.RUB)
    )
}