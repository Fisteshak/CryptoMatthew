package com.example.cryptomatthew.data.local

import com.example.cryptomatthew.data.local.dao.CurrencyDao
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.network.models.Ticker
import com.example.cryptomatthew.models.Currency
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class OfflineCurrenciesRepository @Inject constructor(private val currencyDao: CurrencyDao) {
    val currencies =
        currencyDao.getAllCurrencies()
            .combine(currencyDao.getAllFinancials()) {
                    currList: List<CurrencyEntity>, finList: List<FinancialsEntity> ->
                currList.map { currency ->
                    Currency(
                        currency,
                        finList.find { it.currencyId == currency.id && it.priceCurrency == "USD" },
                        finList.find { it.currencyId == currency.id && it.priceCurrency == "RUB" },
                    )
                }
            }




    suspend fun insertCurrenciesData(tickers: List<Ticker>) {
        currencyDao.insertCurrenciesData(tickers)
    }

    suspend fun insertCurrencyEntity(currencyEntity: CurrencyEntity) {
        currencyDao.insertCurrencyEntity(currencyEntity)
    }

    suspend fun insertFinancialsEntity(financialsEntity: FinancialsEntity) {
        currencyDao.insertFinancialsEntity(financialsEntity)
    }

    suspend fun clearDB() {
        currencyDao.clearDB()
    }
}