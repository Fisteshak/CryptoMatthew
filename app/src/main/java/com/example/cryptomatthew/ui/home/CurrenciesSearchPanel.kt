package com.example.cryptomatthew.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.models.Currency
import java.util.Locale


@Composable
fun CurrenciesSearchPanel(
    innerPadding: PaddingValues,
    currencies: List<Currency>,
    onCurrencyClick: (currency: Currency) -> Unit,
    onFavoriteIconClick: (currencyId: String) -> Unit
) {
    Column(modifier = Modifier.padding(innerPadding)) {

        var searchText by rememberSaveable { mutableStateOf("") }
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Введите название или тикер") },
            maxLines = 1,
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
        )
        CurrencyListHeader(Modifier.padding(vertical = 3.dp))
        CurrencyList(
            currencies
                .filter {
                    it.name.lowercase(Locale.ROOT).contains(searchText.lowercase(Locale.ROOT))
                },
            Modifier.padding(start = 4.dp, end = 16.dp),
            onCurrencyClick,
            onFavoriteIconClick
        )
    }
}

@Composable
fun CurrencyListHeader(modifier: Modifier = Modifier) {
    Row(modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .width(35.dp)
        ) {
            Text(
                text = "#",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            )
        }
        Text(
            text = "Название",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = 6.dp)
        )
        Spacer(
            modifier = Modifier
                .width(20.dp)
                .weight(1f)
        )
        Text(
            text = "Цена",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(end = 16.dp)
        )
        Spacer(
            modifier = Modifier
                .width(40.dp)
                .padding(start = 10.dp)
        )
    }
}

//@Composable
//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//fun HomeScreenPreview() {
//    HomeScreen(currenciesData)
//}
@Composable
fun CurrencyList(
    currencies: List<Currency>,
    modifier: Modifier = Modifier,
    onCurrencyClick: (currency: Currency) -> Unit,
    onFavoriteIconClick: (currencyId: String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        items(currencies.size) { index ->
            CryptoCard(currencies[index], onCurrencyClick, onFavoriteIconClick)
        }
    }
}