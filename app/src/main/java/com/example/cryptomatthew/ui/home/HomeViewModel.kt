package com.example.cryptomatthew.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomatthew.data.CurrenciesRepository
import com.example.cryptomatthew.models.Currencies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
)  : ViewModel() {

    private val _currencies = MutableStateFlow<Currencies>(emptyList())
    val currencies: StateFlow<Currencies> = _currencies.asStateFlow()

    init {

        runTimer()

        getCurrencies()
    }

    fun runTimer() {
        viewModelScope.launch {
            while (true) {
                syncDatabaseWithNetwork()

                delay(1.minutes.toJavaDuration())
            }
        }
    }

    fun syncDatabaseWithNetwork() {
        currenciesRepository.syncDatabaseWithNetwork()
    }

    fun getCurrencies() {
        viewModelScope.launch {
            currenciesRepository.getCurrencies().collect { x ->
                _currencies.update { x }
                Log.d("HomeViewModel", x.toString())
            }

        }
    }




}