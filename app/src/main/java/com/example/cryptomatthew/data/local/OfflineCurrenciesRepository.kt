package com.example.cryptomatthew.data.local

import com.example.cryptomatthew.data.local.dao.CurrencyDao
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.models.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class OfflineCurrenciesRepository @Inject constructor(private val currencyDao: CurrencyDao) {
    fun getCurrencies(): Flow<List<Currency>> {
        val financials = currencyDao.getAllFinancials()

        val currencies = currencyDao.getAllCurrencies()
            .combine(financials) {
                currList: List<CurrencyEntity>, finList: List<FinancialsEntity> ->
                    currList.map { currency ->
                        Currency(
                            currency,
                            finList.find { it.currencyId == currency.id },
                            finList.find { it.currencyId == currency.id },
                        )
                    }
            }
        return currencies
    }


    suspend fun insertCurrencyEntity(currencyEntity: CurrencyEntity) {
        currencyDao.insertCurrencyEntity(currencyEntity)
    }

    suspend fun insertFinancialsEntity(financialsEntity: FinancialsEntity) {
        currencyDao.insertFinancialsEntity(financialsEntity)
    }

    suspend fun clearDB() {
        currencyDao.deleteAllCurrencyEntities()
        currencyDao.deleteAllFinancialEntities()
    }
}