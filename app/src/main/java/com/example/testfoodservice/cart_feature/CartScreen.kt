package com.example.testfoodservice.cart_feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.models.product.Product
import com.example.testfoodservice.AmountButtons
import com.example.testfoodservice.LoadImage
import com.example.testfoodservice.R
import com.example.testfoodservice.decreaseBy100
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val products by viewModel.productsFromCart.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose {
            //TODO if need
        }
    }

    Scaffold(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_16)),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_cart),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            if (products.isEmpty())
                EmptyCart()
            else
                CatalogLazyColumn(
                    products = products,
                    addProductToCart = viewModel::addProductToCart,
                    removeProductFromCart = viewModel::removeProductFromCart
                )
        }
    }
}

@Composable
fun EmptyCart(modifier: Modifier = Modifier) {
    Surface(
        content = {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.empty_cart),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    )
}

@Composable
fun CatalogLazyColumn(
    modifier: Modifier = Modifier,
    products: List<Product>,
    addProductToCart: (id: Int) -> Unit,
    removeProductFromCart: (id: Int) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
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
    modifier: Modifier = Modifier,
    product: Product,
    onAddProduct: () -> Unit,
    onRemoveProduct: () -> Unit
) {
    Row {
        Column(modifier = modifier.weight(1f)) {
            LoadImage(
                modifier = modifier
                    .padding(end = dimensionResource(R.dimen.padding_8))
                    .size(100.dp, 100.dp)
            )
        }

        Column(
            modifier = modifier.weight(2f)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            AmountButtons(
                amount = product.amount,
                onIncrement = onAddProduct,
                onDecrement = onRemoveProduct
            )
        }

        Column(
            modifier = modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(text = product.totalPrice.decreaseBy100().toString())
        }
    }
}