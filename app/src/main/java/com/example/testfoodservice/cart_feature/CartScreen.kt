package com.example.testfoodservice.cart_feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.domain.models.ProductModel
import com.example.testfoodservice.AmountButtons
import com.example.testfoodservice.LoadImage
import com.example.testfoodservice.R
import com.example.testfoodservice.WideButton
import com.example.testfoodservice.decreaseBy100
import com.example.testfoodservice.ui.theme.Grey
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val products by viewModel.productsFromCart.collectAsStateWithLifecycle()
    val cartPrice by viewModel.cartPrice.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose {
            //TODO if need
        }
    }

    Scaffold(
        modifier = modifier,
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
        },
        bottomBar = {
            if (cartPrice != 0)
                WideButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_16)),
                    onClick = {},
                    text = stringResource(R.string.order, cartPrice.decreaseBy100())
                )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
        ) {
            if (products.isEmpty())
                EmptyCart()
            else
                CatalogLazyColumn(
                    productModels = products,
                    addProductToCart = viewModel::addProductToCart,
                    removeProductFromCart = viewModel::removeProductFromCart
                )
        }
    }
}

@Composable
fun CatalogLazyColumn(
    modifier: Modifier = Modifier,
    productModels: List<ProductModel>,
    addProductToCart: (id: Int) -> Unit,
    removeProductFromCart: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(productModels.size) { index ->
            CartItem(
                productModel = productModels[index],
                onAddProduct = { addProductToCart(productModels[index].id) },
                onRemoveProduct = { removeProductFromCart(productModels[index].id) }
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Grey
            )
        }
    }
}

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    productModel: ProductModel,
    onAddProduct: () -> Unit,
    onRemoveProduct: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_16))
    ) {
        val (image, name, amountButtons, price) = createRefs()

        LoadImage(
            modifier = Modifier
                .size(dimensionResource(R.dimen.image_size_100))
                .padding(end = dimensionResource(R.dimen.padding_16))
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = productModel.name,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(parent.top)
                start.linkTo(image.end)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        Row(
            modifier = Modifier
                .constrainAs(price) {
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            verticalAlignment = Alignment.Bottom
        ) {
            AmountButtons(
                amount = productModel.amount,
                onIncrement = onAddProduct,
                onDecrement = onRemoveProduct,
                modifier = Modifier.fillMaxWidth(0.5f)
            )

            Text(
                text = stringResource(
                    R.string.price,
                    productModel.totalPrice.decreaseBy100().toString()
                ),
                modifier = Modifier.fillMaxWidth(0.5f),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.End
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
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center

                )
            }
        }
    )
}