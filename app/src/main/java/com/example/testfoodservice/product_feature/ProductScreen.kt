package com.example.testfoodservice.product_feature

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testfoodservice.catalog_feature.LoadImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {

    val product by viewModel.clickedProduct.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            // Верхнее изображение
            LoadImage(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))

            // Название
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Описание
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Вес
            Text(
                text = "Weight: ${product.measure} ${product.measureUnit}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Энергия на 100 грамм
            Text(
                text = "Energy per 100 grams: ${product.energyPer100Grams} kcal",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Белки на 100 грамм
            Text(
                text = "Proteins per 100 grams: ${product.proteinsPer100Grams} g",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Жиры на 100 грамм
            Text(
                text = "Fats per 100 grams: ${product.fatsPer100Grams} g",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Углеводы на 100 грамм
            Text(
                text = "Carbohydrates per 100 grams: ${product.carbohydratesPer100Grams} g",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Кнопка "В корзину"
            Button(
                onClick = {
                    viewModel.addProductToCart(product.id)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "В корзину за ${product.priceCurrent} руб.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}