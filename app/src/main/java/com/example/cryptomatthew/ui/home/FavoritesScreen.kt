package com.example.cryptomatthew.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptomatthew.models.Currency
import com.example.cryptomatthew.ui.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
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
                    Text("Избранное")
                }
            )
        },

        ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            var selectedIndex by remember { mutableIntStateOf(0) }
            val options = listOf("Избранное", "Уведомления")

            SingleChoiceSegmentedButtonRow(modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)) {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onClick = { selectedIndex = index },
                        selected = index == selectedIndex,
                        label = { Text(label) }
                    )
                }

            }
            CurrenciesSearchPanel(
                currencies.filter { if (selectedIndex == 0) it.isFavorite else it.rateNotificationsEnabled },
                onNavigateToCurrency,
                { viewModel.toggleFavorite(currencyId = it) },
                { viewModel.toggleNotificationsEnabled(it) },
            )
        }
    }
}

