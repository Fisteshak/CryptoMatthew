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
import com.example.cryptomatthew.models.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    currencies: List<Currency>,
    onCurrencyClick: (currency: Currency) -> Unit,
    onFavoriteIconClick: (currencyId: String) -> Unit,
    onNotificationsEnabledIconClick: (currencyId: String) -> Unit,
    modifier: Modifier = Modifier,
) {

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

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth().padding(6.dp)) {
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
                onCurrencyClick,
                onFavoriteIconClick,
                onNotificationsEnabledIconClick
            )
        }
    }
}

