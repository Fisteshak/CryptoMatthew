package com.example.cryptomatthew.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.cryptomatthew.ui.theme.CryptoMatthewTheme

@Composable
fun CryptoMatthewApp() {
    CryptoMatthewTheme {
        val navController = rememberNavController()

        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color =  MaterialTheme.colorScheme.background) {
            HomeScreen()
        }
    }

}