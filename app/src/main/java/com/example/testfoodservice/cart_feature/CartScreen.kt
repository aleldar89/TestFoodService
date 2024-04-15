package com.example.testfoodservice.cart_feature

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CartScreen() {
    Text(
        text = "Empty cart",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

