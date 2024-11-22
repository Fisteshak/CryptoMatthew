package com.example.cryptomatthew.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.models.Currency


@Composable
fun HomeScreen(
    currencies: List<Currency>,
    modifier: Modifier = Modifier,
    onCurrencyClick: (currency: Currency) -> Unit
) {

    Column {

        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

                items(currencies.size) {
                    index -> CryptoCard(currencies[index], onCurrencyClick)
                }
        }
    }
}

//@Composable
//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//fun HomeScreenPreview() {
//    HomeScreen(currenciesData)
//}