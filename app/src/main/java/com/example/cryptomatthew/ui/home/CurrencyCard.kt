package com.example.cryptomatthew.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.models.Currency

@Composable
fun CryptoCard(currency: Currency, onCurrencyClick: (currency: Currency) -> Unit, modifier: Modifier = Modifier) {


    Row(
        modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(4.dp)
            .clickable(onClick = {
                onCurrencyClick(currency)
            }),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Text(
            text = currency.rank.toString(),

            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = currency.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier
            .width(20.dp)
            .weight(1f))
        Text(
            text = currency.finsUSD?.price?.formatLong() ?: "",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(end = 16.dp)

        )


    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
fun CryptoCardPreview() {
    //CryptoCard(Currency(, 1, "Bitcoin", "BTC", Financials(1000000.0)))
}