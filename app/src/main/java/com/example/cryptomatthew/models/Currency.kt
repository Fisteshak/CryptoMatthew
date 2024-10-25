package com.example.cryptomatthew.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptomatthew.data.network.models.Ticker

data class Currency(
    val rank: Int,
    val name: String,
    val symbol: String,
    val finsUSD: Financials,
    val finsRUB: Financials,
    val history: List<Tick>? = null,
) {
    constructor(ticker: Ticker) : this(
        ticker.rank.toInt(),
        ticker.name,
        ticker.symbol,
        Financials(ticker.quotes.USD),
        Financials(ticker.quotes.RUB)
    )
}

typealias Currencies = List<Currency>