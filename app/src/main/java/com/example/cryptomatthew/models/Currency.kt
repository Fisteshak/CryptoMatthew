package com.example.cryptomatthew.models

import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.network.models.Ticker

data class Currency(
    val rank: Int,
    val name: String,
    val symbol: String,
    val finsUSD: Financials? = null,
    val finsRUB: Financials? = null,
    val history: List<Tick>? = null,
) {
    constructor(ticker: Ticker) : this(
        ticker.rank.toInt(),
        ticker.name,
        ticker.symbol,
        Financials(ticker.quotes.USD),
        Financials(ticker.quotes.RUB)
    )

    constructor(currencyEntity: CurrencyEntity) : this(
        currencyEntity.rank,
        currencyEntity.name,
        currencyEntity.symbol
    )


}


typealias Currencies = List<Currency>