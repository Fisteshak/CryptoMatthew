package com.example.cryptomatthew.data

import android.util.Log
import com.example.cryptomatthew.data.local.OfflineCurrenciesRepository
import com.example.cryptomatthew.data.local.entities.TickEntity
import com.example.cryptomatthew.data.network.NetworkCurrenciesRepository
import com.example.cryptomatthew.models.Currency
import com.example.cryptomatthew.models.Tick
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class CurrenciesRepository @Inject constructor(
    private val networkCurrenciesRepository: NetworkCurrenciesRepository,
    private val offlineCurrenciesRepository: OfflineCurrenciesRepository
) {


    @OptIn(FlowPreview::class)
    fun getCurrencies(): Flow<List<Currency>> {
        return offlineCurrenciesRepository.currencies.debounce(3.seconds)
    }


    private suspend fun clearDB() {
        offlineCurrenciesRepository.clearDB()
    }

    suspend fun updateCurrencies() {
        networkCurrenciesRepository.latestTickers.collect {
            val tickers = it.body()
            Log.d("Currencies Repo", "tickers request response: ${it.code()}")

            if (it.code() == 200 && tickers != null) {

                clearDB()

                offlineCurrenciesRepository.insertCurrenciesData(tickers)
            } else {
                Log.d("Currencies Repo", "error while updating tickers: ${it.code()}, ${it.message()}")
            }
        }
    }

    suspend fun updateCurrencyHistory(currencyId: String) {
        val response = networkCurrenciesRepository.getTickerHistory(currencyId)
        if (response == null) return;
        val ticks = response.body()
        if (response.code() == 200 && ticks != null) {
            val history = ticks.map { TickEntity(currencyId, it) }

            offlineCurrenciesRepository.insertCurrencyHistory(history)
        }

    }

    suspend fun getCurrencyHistory(currencyId: String): List<Tick> {
        val ticks = offlineCurrenciesRepository.getCurrencyHistory(currencyId)
        return ticks.map {
            Tick(it)
        }

    }




}