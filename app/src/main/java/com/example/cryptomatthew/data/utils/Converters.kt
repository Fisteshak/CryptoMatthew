package com.example.cryptomatthew.data.utils

import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.network.models.Ticker

fun tickerToCurrencyAndFinancialsEntity(ticker: Ticker): Triple<CurrencyEntity, FinancialsEntity, FinancialsEntity>  {
    val currencyEntity = CurrencyEntity(ticker)
    val financialsEntityUSD = FinancialsEntity(ticker.quotes.USD, ticker.id, "USD")
    val financialsEntityRUB = FinancialsEntity(ticker.quotes.RUB, ticker.id, "RUB")
    return Triple(currencyEntity, financialsEntityUSD, financialsEntityRUB)
}