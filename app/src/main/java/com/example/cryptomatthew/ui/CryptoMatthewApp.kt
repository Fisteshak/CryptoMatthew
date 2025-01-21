package com.example.cryptomatthew.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptomatthew.ui.currencyScreen.CurrencyInfoScreen
import com.example.cryptomatthew.ui.home.FavoritesScreen
import com.example.cryptomatthew.ui.home.HomeScreen
import com.example.cryptomatthew.ui.notifications.NotificationScreen
import com.example.cryptomatthew.ui.theme.CryptoMatthewTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoMatthewApp(
    navController: NavHostController = rememberNavController()
) {
    CryptoMatthewTheme {

        val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        }
        val viewModel: HomeViewModel = hiltViewModel(viewModelStoreOwner = viewModelStoreOwner)

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            bottomBar = {
                Column {
                    AnimatedVisibility(viewModel.isShowingNoConnectionBar) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(color = MaterialTheme.colorScheme.errorContainer),
                            contentAlignment = Alignment.Center,

                            ) {
                            Text("Офлайн-режим", modifier = Modifier.padding(vertical = 4.dp))
                        }

                    }
                    BottomNavigation(
                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ) {
                        topLevelRoutes.forEach { topLevelRoute ->
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        topLevelRoute.icon, contentDescription = topLevelRoute.name
                                    )
                                },
                                label = { Text(topLevelRoute.name) },
                                selected = currentDestination?.route == topLevelRoute.route.name,
                                onClick = {
                                    navController.navigate(topLevelRoute.route.name) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                }

                            )

                        }
                    }
                }
            },

            ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.Home.name,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(route = Routes.Home.name) {
                    CompositionLocalProvider(
                        LocalViewModelStoreOwner provides viewModelStoreOwner
                    ) {

                        HomeScreen(
                            { navController.navigate("${Routes.CurrencyInfo.name}/${it.id}") }
                        )
                    }
                }
                composable(route = Routes.Favorites.name) {
                    CompositionLocalProvider(
                        LocalViewModelStoreOwner provides viewModelStoreOwner
                    ) {
                        FavoritesScreen(
                            { navController.navigate("${Routes.CurrencyInfo.name}/${it.id}") },
                        )
                    }
                }
                composable(
                    route = "${Routes.CurrencyInfo.name}/{currency_id}",
                    arguments = listOf(navArgument(name = "currency_id") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    val currencyId =
                        navBackStackEntry.arguments?.getString("currency_id")
                    if (currencyId != null) {
                        CompositionLocalProvider(
                            LocalViewModelStoreOwner provides viewModelStoreOwner
                        ) {
                            CurrencyInfoScreen(currencyId)
                        }
                    } else Log.d("NavHost", "CryptoMatthewApp: navigation without id")
                }
                composable(route = Routes.Scanner.name) {
                    CompositionLocalProvider(
                        LocalViewModelStoreOwner provides viewModelStoreOwner
                    ) {
                        NotificationScreen()
                    }
                }

            }

        }
    }

}