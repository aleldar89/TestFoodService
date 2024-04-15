package com.example.testfoodservice

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testfoodservice.cart_feature.CartScreen
import com.example.testfoodservice.catalog_feature.CatalogScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Catalog.route
    ) {
        composable(route = Catalog.route) {
            CatalogScreen(
                onNavigateToCart = {
                    navController.navigateSingleTopTo(Cart.route)
                }
            )
        }
        composable(route = Cart.route) {
            CartScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }