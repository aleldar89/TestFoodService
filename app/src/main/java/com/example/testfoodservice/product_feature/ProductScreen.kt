package com.example.testfoodservice.product_feature

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testfoodservice.LoadImage
import com.example.testfoodservice.R
import com.example.testfoodservice.WideButton
import com.example.testfoodservice.decreaseBy100
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel()
) {

    val product by viewModel.clickedProduct.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_16))
    ) {
        item {

            Box(
                modifier = modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "")
                }
                LoadImage(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(R.dimen.padding_24))
                )
            }

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier.padding(bottom = dimensionResource(R.dimen.padding_8))
            )

            Text(
                text = product.description,
                modifier = modifier.padding(bottom = dimensionResource(R.dimen.padding_24))
            )

            TextRow(
                labelResId = R.string.energy_value,
                text = "${product.energyPer100Grams}",
                endTextResId = R.string.calories
            )

            TextRow(
                labelResId = R.string.weight,
                text = "${product.measure}"
            )

            TextRow(
                labelResId = R.string.proteins,
                text = "${product.fatsPer100Grams}"
            )

            TextRow(
                labelResId = R.string.fats,
                text = "${product.fatsPer100Grams}"
            )

            TextRow(
                labelResId = R.string.carbohydrates,
                text = "${product.carbohydratesPer100Grams}"
            )

            WideButton(
                onClick = { viewModel.addProductToCart(product.id) },
                text = stringResource(R.string.to_cart, product.priceCurrent.decreaseBy100())
            )
        }
    }
}

@Composable
fun TextRow(
    modifier: Modifier = Modifier,
    @StringRes labelResId: Int,
    text: String,
    @StringRes endTextResId: Int = R.string.gramms
) {

    Row(
        modifier = modifier
            .padding(bottom = dimensionResource(R.dimen.padding_14))
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = labelResId),
            modifier = modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        Text(
            text = stringResource(id = endTextResId, text),
            modifier = modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}
