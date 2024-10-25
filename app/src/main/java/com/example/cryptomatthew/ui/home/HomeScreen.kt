package com.example.cryptomatthew.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptomatthew.models.Currency

private val homeViewModel = HomeViewModel(LocalContext.current)

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {


    val currencies by homeViewModel.currencies.collectAsStateWithLifecycle()

    Column {
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