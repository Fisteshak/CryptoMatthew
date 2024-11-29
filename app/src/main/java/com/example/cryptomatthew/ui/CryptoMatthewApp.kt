package com.example.cryptomatthew.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptomatthew.ui.currencyScreen.CurrencyInfoScreen
import com.example.cryptomatthew.ui.home.HomeScreen
import com.example.cryptomatthew.ui.home.HomeViewModel
import com.example.cryptomatthew.ui.theme.CryptoMatthewTheme


@Composable
fun CryptoMatthewApp(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
    ) {
    CryptoMatthewTheme {
        val currencies by viewModel.currencies.collectAsStateWithLifecycle()
        val histories by viewModel.histories.collectAsStateWithLifecycle()


        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color =  MaterialTheme.colorScheme.background) {
            NavHost(
                navController = navController,
                startDestination = Destinations.Home.name,
                modifier = Modifier
            ) {
                composable(route = Destinations.Home.name) {
                    HomeScreen(currencies, onCurrencyClick = {
                        navController.navigate("${Destinations.CurrencyInfo.name}/${it.id}")
                    })
                }
                composable(
                    route = "${Destinations.CurrencyInfo.name}/{currency_id}",
                    arguments = listOf(navArgument(name = "currency_id") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    val currencyId = navBackStackEntry.arguments?.getString("currency_id")
                    if (currencyId != null) {
                        viewModel.updateCurrencyHistory(currencyId)

                        CurrencyInfoScreen(
                            currencies.find { it.id == currencyId }!!,
                            histories.find { it.currencyId == currencyId },

                        )
                    }
                    else
                        Log.d("NavHost", "CryptoMatthewApp: navigation without id")
                }

            }

        }
    }

}