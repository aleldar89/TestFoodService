package com.example.testfoodservice

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.testfoodservice.catalog_feature.CatalogScreen
import com.example.testfoodservice.ui.theme.TestFoodServiceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashScreenDuration = 2000L

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestFoodServiceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SplashScreen()
                    Handler(Looper.getMainLooper()).postDelayed({
                        setContent {
//                            CatalogScreen()
                            AppScreen()
                        }
                    }, splashScreenDuration)
                }
            }
        }
    }
}