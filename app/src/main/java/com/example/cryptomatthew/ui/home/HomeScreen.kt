package com.example.cryptomatthew.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val homeViewModel: HomeViewModel = viewModel()

    val currencies by homeViewModel.currencies.collectAsStateWithLifecycle()

    Column {
        Row (modifier = Modifier.fillMaxWidth()) {

            Button(
                onClick = { homeViewModel.getCurrencies() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Load")
            }

        }
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

                items(currencies.size) {
                    index -> CryptoCard(currencies[index])
                }
        }
    }
}

//@Composable
//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//fun HomeScreenPreview() {
//    HomeScreen(currenciesData)
//}