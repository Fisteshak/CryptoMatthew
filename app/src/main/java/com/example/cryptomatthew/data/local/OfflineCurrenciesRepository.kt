package com.example.cryptomatthew.data.local

import com.example.cryptomatthew.data.local.dao.CurrencyDao
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.local.entities.TickEntity
import com.example.cryptomatthew.data.network.models.NetworkTicker
import com.example.cryptomatthew.models.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class OfflineCurrenciesRepository @Inject constructor(private val currencyDao: CurrencyDao) {
    val currenciesFlow: Flow<List<Currency>>
        get() {
            return currencyDao.getAllCurrenciesFlow()
                .combine(currencyDao.getAllFinancialsFlow()) { currList: List<CurrencyEntity>, finList: List<FinancialsEntity> ->
                    combineCurrencyAndFinancialsEntitiesToCurrency(currList, finList)
                }

        }

    private fun combineCurrencyAndFinancialsEntitiesToCurrency(
        currList: List<CurrencyEntity>,
        finList: List<FinancialsEntity>
    ): List<Currency> {
        return currList.map { currency ->
            Currency(
                currency,
                finList.find { it.currencyId == currency.id && it.priceCurrency == "USD" },
                finList.find { it.currencyId == currency.id && it.priceCurrency == "RUB" },
            )
        }
    }

    suspend fun getCurrencies(): List<CurrencyEntity> {
        return currencyDao.getAllCurrencies()
    }

    suspend fun getCurrenciesWithFinancials(): List<Currency> {
        return combineCurrencyAndFinancialsEntitiesToCurrency(
            currencyDao.getAllCurrencies(),
            currencyDao.getAllFinancials()
        )

    }


    // takes List of Pairs, where first value is ticker get from API and second value is isFavorite for this ticker
    suspend fun insertCurrenciesData(tickers: List<NetworkTicker>) {
        currencyDao.insertCurrenciesData(tickers)
    }

    suspend fun insertCurrencyEntity(currencyEntity: CurrencyEntity) {
        currencyDao.insertCurrencyEntity(currencyEntity)
    }

    suspend fun insertFinancialsEntity(financialsEntity: FinancialsEntity) {
        currencyDao.insertFinancialsEntity(financialsEntity)
    }

    suspend fun insertCurrencyHistory(ticks: List<TickEntity>) {
        currencyDao.insertCurrencyTicks(ticks)
    }

    suspend fun getCurrencyHistory(tickerId: String): List<TickEntity> {
        return currencyDao.getCurrencyTicks(tickerId)
    }

    suspend fun clearDB() {
        currencyDao.clearDB()
    }

    suspend fun clearHistory(currencyId: String) {
        currencyDao.deleteTickEntities(currencyId)
    }

    suspend fun updateCurrencyIsFavorite(currencyId: String, isFavorite: Boolean) {
        currencyDao.updateCurrencyIsFavorite(currencyId, isFavorite)
    }

    suspend fun updateCurrencyRateNotificationsEnabled(currencyId: String, isFavorite: Boolean) {
        currencyDao.updateCurrencyRateNotificationEnabled(currencyId, isFavorite)
    }
}