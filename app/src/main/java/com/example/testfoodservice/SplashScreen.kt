package com.example.testfoodservice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFA500)
    ) {
        Image(
            modifier = Modifier.size(70.dp, 30.dp),
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.splash_screen_logo),
            contentDescription = ""
        )
    }
}