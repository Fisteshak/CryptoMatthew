package com.example.cryptomatthew.ui.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cryptomatthew.models.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    currencies: List<Currency>,
    modifier: Modifier = Modifier,
    onCurrencyClick: (currency: Currency) -> Unit,
    onFavoriteIconClick: (currencyId: String) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Избранное")
                }
            )
        },

    ) { innerPadding ->
        CurrenciesSearchPanel(innerPadding, currencies, onCurrencyClick, onFavoriteIconClick)
    }
}

