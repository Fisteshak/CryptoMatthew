package com.example.cryptomatthew.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomatthew.data.CurrenciesRepository
import com.example.cryptomatthew.models.Currency
import com.example.cryptomatthew.models.History
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
)  : ViewModel() {

    private val _currencies = MutableStateFlow<List<Currency>>(emptyList())
    val currencies: StateFlow<List<Currency>> = _currencies.asStateFlow()

    private val _histories = MutableStateFlow<MutableList<History>>(mutableListOf())
    val histories: StateFlow<List<History>> = _histories.asStateFlow()

    init {
        runDataNetworkUpdateCoroutine()
        runDataUpdateFlow()
    }

    fun updateCurrencyHistory(currencyId: String) {
        viewModelScope.launch {
            Log.d( "ViewModel", "starting to update history for $currencyId")

            with (Dispatchers.IO) {
                currenciesRepository.updateCurrencyHistory(currencyId)
                val history = _histories.value.find { it.currencyId == currencyId }
                val newHistory = currenciesRepository.getCurrencyHistory(currencyId)

                if (history == null) {
                    _histories.value.add(newHistory)
                    Log.d( "ViewModel", "added history for $currencyId: ${newHistory}")
                } else {
                    _histories.value[_histories.value.indexOf(history)] = newHistory
                    Log.d( "ViewModel", "updated history for $currencyId: ${newHistory}")
                }


            }
        }
    }

    private fun runDataNetworkUpdateCoroutine() {
        viewModelScope.launch {
            with (Dispatchers.IO) {
                currenciesRepository.updateCurrencies()

            }
        }
    }

    private fun runDataUpdateFlow() {
        viewModelScope.launch {
            currenciesRepository.getCurrencies().collect { x ->
                _currencies.update { x }
                Log.d("HomeViewModel", "updated tickers ${x.toString()}")
            }

        }
    }

    fun toggleFavorite(currencyId: String) {

        viewModelScope.launch {
            val x = _currencies.value.find { it.id == currencyId }
            if (x != null) {
                //_currencies.update { it.forEach { x -> x.isFavorite = true }; it}
                //x.isFavorite = !x.isFavorite
                currenciesRepository.setIsFavorite(currencyId, !x.isFavorite)
            } else {
                Log.d(
                    "HomeViewModel",
                    "setIsFavorite: can't find currency with id = $currencyId"
                )
            }
        }


    }



}