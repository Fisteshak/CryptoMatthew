package com.example.cryptomatthew.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomatthew.data.CurrenciesRepository
import com.example.cryptomatthew.models.Currency
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

    init {

        runTimer()

        getCurrencies()
    }

    private fun runTimer() {
        viewModelScope.launch {
            with (Dispatchers.IO) {
                currenciesRepository.updateCurrencies()

            }
        }
    }



    private fun getCurrencies() {
        viewModelScope.launch {
            currenciesRepository.getCurrencies().collect { x ->
                _currencies.update { x }
                Log.d("HomeViewModel", x.toString())
            }

        }
    }




}