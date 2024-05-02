package com.example.testfoodservice

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testfoodservice.cart_feature.CartScreen
import com.example.testfoodservice.catalog_feature.CatalogScreen
import com.example.testfoodservice.product_feature.ProductScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.CATALOG_ROUTE
    ) {
        composable(route = AppDestination.CATALOG_ROUTE) {
            CatalogScreen(
                onNavigateToCart = {
                    navController.navigate(route = AppDestination.CART_ROUTE)
                },

                onNavigateToProduct = { productId ->
                    navController.navigate(route = "${AppDestination.PRODUCT_ROUTE}/$productId")
                }
            )
        }

        composable(route = AppDestination.CART_ROUTE) {
            CartScreen(navController = navController)
        }

        composable(route = "${AppDestination.PRODUCT_ROUTE}/{productId}") {
            ProductScreen(navController = navController)
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