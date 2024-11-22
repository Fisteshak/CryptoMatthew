package com.example.cryptomatthew.models

import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.network.models.NetworkTicker

data class Currency(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    var finsUSD: Financials? = null,
    var finsRUB: Financials? = null,
    var history: List<Tick>? = null,
) {
    constructor(ticker: NetworkTicker) : this(
        ticker.id,
        ticker.rank.toInt(),
        ticker.name,
        ticker.symbol,
        Financials(ticker.quotes.USD),
        Financials(ticker.quotes.RUB)
    )

    constructor(currencyEntity: CurrencyEntity, finsUSD: FinancialsEntity? = null, finsRUB: FinancialsEntity? = null) : this(
        currencyEntity.id,
        currencyEntity.rank,
        currencyEntity.name,
        currencyEntity.symbol,
    ) {
        if (finsUSD != null) this.finsUSD = Financials(finsUSD)
        if (finsRUB != null) this.finsRUB = Financials(finsRUB)
    }


}


