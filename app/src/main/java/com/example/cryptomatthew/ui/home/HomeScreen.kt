package com.example.cryptomatthew.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptomatthew.models.Currency
import com.example.cryptomatthew.ui.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCurrency: (currency: Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val currencies by viewModel.currencies.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Монеты")
                }
            )
        }
    ) { innerPadding ->
        CurrenciesSearchPanel(
            currencies,
            onNavigateToCurrency,
            { viewModel.toggleFavorite(currencyId = it) },
            { viewModel.toggleNotificationsEnabled(it) },
            Modifier.padding(innerPadding)
        )
    }
}