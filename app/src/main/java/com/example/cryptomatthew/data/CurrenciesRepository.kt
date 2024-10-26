package com.example.cryptomatthew.data

import android.util.Log
import com.example.cryptomatthew.data.local.OfflineCurrenciesRepository
import com.example.cryptomatthew.data.network.NetworkCurrenciesRepository
import com.example.cryptomatthew.data.utils.tickerToCurrencyAndFinancialsEntity
import com.example.cryptomatthew.models.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrenciesRepository @Inject constructor(
    private val networkCurrenciesRepository: NetworkCurrenciesRepository,
    private val offlineCurrenciesRepository: OfflineCurrenciesRepository
) {

    fun getCurrencies(): Flow<List<Currency>> {
        return offlineCurrenciesRepository.getCurrencies()
    }

    suspend fun clearDB() {
        offlineCurrenciesRepository.clearDB()
    }

    fun syncDatabaseWithNetwork(coroutineScope: CoroutineScope) {


        networkCurrenciesRepository.fetchTickers(
            onResponse = {code, tickers ->
                if (code == 200 && tickers != null) {
                    coroutineScope.launch {
                        clearDB()

                        tickers.forEach {
                            val (currencyEntity, financialsEntityUSD, financialsEntityRUB) =
                                tickerToCurrencyAndFinancialsEntity(it)

                            offlineCurrenciesRepository.insertCurrencyEntity(currencyEntity)
                            offlineCurrenciesRepository.insertFinancialsEntity(financialsEntityUSD)
                            offlineCurrenciesRepository.insertFinancialsEntity(financialsEntityRUB)
                        }

                    }
                }
            },
            onFailure = {t ->
                Log.d("Currencies Repo", "syncDatabaseWithNetwork: error while getting tickers: ${t.message}")
            }
        )
    }



}