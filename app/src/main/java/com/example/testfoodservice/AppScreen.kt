package com.example.testfoodservice

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.testfoodservice.ui.theme.TestFoodServiceTheme

@Composable
fun AppScreen() {
    TestFoodServiceTheme {
        val navController = rememberNavController()
        
        AppNavHost(navController = navController)
    }
}