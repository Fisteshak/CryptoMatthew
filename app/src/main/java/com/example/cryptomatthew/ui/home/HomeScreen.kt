package com.example.cryptomatthew.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.models.Currency


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
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
                    Text("Монеты")
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            var selectedIndex by remember { mutableStateOf(0) }
            val options = listOf("Все", "Избранное")


            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onClick = { selectedIndex = index },
                        selected = index == selectedIndex,
                        icon = { }
                    ) {
                        Text(label)
                    }
                }

            }
            CurrencyListHeader(Modifier.padding(vertical = 3.dp))
            CurrencyList(
                if (selectedIndex == 0) currencies else currencies.filter { it.isFavorite },
                Modifier.padding(start = 4.dp, end = 16.dp),
                onCurrencyClick,
                onFavoriteIconClick
            )
        }
    }
}

@Composable
private fun CurrencyListHeader(modifier: Modifier = Modifier) {
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