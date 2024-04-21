package com.example.testfoodservice.cart_feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.product.Product
import com.example.testfoodservice.catalog_feature.CounterRow
import com.example.testfoodservice.catalog_feature.LoadImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel()
) {
    val products by viewModel.productsFromCart.collectAsStateWithLifecycle()

    if (products.isEmpty()) {
        Text(
            text = "Empty cart",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        CatalogLazyColumn(
            products = products,
            addProductToCart = viewModel::addProductToCart,
            removeProductFromCart = viewModel::removeProductFromCart
        )
    }
}

@Composable
fun CatalogLazyColumn(
    products: List<Product>,
    addProductToCart: (id: Int) -> Unit,
    removeProductFromCart: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products.size) { index ->
            CartItem(
                product = products[index],
                onAddProduct = { addProductToCart(products[index].id) },
                onRemoveProduct = { removeProductFromCart(products[index].id) }
            )
        }
    }
}

@Composable
fun CartItem(
    product: Product,
    onAddProduct: () -> Unit,
    onRemoveProduct: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        LoadImage(
            modifier = Modifier
                .size(100.dp, 100.dp)
                .padding(16.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = product.name,
                style = TextStyle(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            CounterRow(
                amount = product.amount,
                onIncrement = onAddProduct,
                onDecrement = onRemoveProduct
            )

            Text(
                text = product.totalPrice.toString(),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
