package com.example.cryptomatthew.data

import com.example.cryptomatthew.data.local.OfflineCurrenciesRepository
import com.example.cryptomatthew.data.network.NetworkCurrenciesRepository
import com.example.cryptomatthew.data.network.models.Ticker
import com.example.cryptomatthew.data.utils.tickerToCurrencyAndFinancialsEntity
import com.example.cryptomatthew.models.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrenciesRepository @Inject constructor(
    private val networkCurrenciesRepository: NetworkCurrenciesRepository,
    private val offlineCurrenciesRepository: OfflineCurrenciesRepository
) {


    fun getCurrencies(): Flow<List<Currency>> {
        return offlineCurrenciesRepository.getCurrencies()
    }


    private suspend fun clearDB() {
        offlineCurrenciesRepository.clearDB()
    }

    suspend fun updateCurrencies() {
        networkCurrenciesRepository.latestTickers.collect {
            val tickers = it.body()
            if (it.code() == 200 && tickers != null) {

                clearDB()

                for (ticker in tickers) {
                    insertCurrency(ticker)
                }
            }
        }


    }
//                Log.d("Currencies Repo", "syncDatabaseWithNetwork: error while getting tickers: ${t.message}")

    private suspend fun insertCurrency(it: Ticker) {
        val (currencyEntity, financialsEntityUSD, financialsEntityRUB) =
            tickerToCurrencyAndFinancialsEntity(it)

        offlineCurrenciesRepository.insertCurrencyEntity(currencyEntity)
        offlineCurrenciesRepository.insertFinancialsEntity(
            financialsEntityUSD
        )
        offlineCurrenciesRepository.insertFinancialsEntity(
            financialsEntityRUB
        )
    }


}