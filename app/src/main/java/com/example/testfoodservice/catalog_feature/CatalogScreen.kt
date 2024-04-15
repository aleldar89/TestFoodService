package com.example.testfoodservice.catalog_feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models_api.tag.Tag
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import com.example.models_api.product.Product

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel(),
    onNavigateToCart: () -> Unit = {},
) {
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val catalogTags by viewModel.tags.collectAsStateWithLifecycle()
    val products by viewModel.products.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CustomAppBar(
                onClickMenu = { showBottomSheet = true },
                onClickCart = onNavigateToCart
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            CategoryRow(categories)
            ProductGrid(products)
        }

        if (showBottomSheet) {
            val scope = rememberCoroutineScope()
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Подобрать блюда",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    catalogTags.forEach { tag ->
                        TagRow(tag = tag)
                    }

                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = "Готово")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductGrid(products: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products.size) { index ->
            ProductCatalogCard(product = products[index])
        }
    }
}

@Composable
fun ProductCatalogCard(
    product: Product
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            LoadImage()
            if (product.priceOld != null)
                LoadIcon()
        }

        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = product.name, maxLines = 1)
            Text(text = "${product.measure} ${product.measureUnit}")
        }
        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp
            )
        ) {
            Text(text = "${product.priceCurrent.decreaseBy100()}")
            if (product.priceOld != null) {
                Text(
                    text = "${product.priceOld.let { it?.decreaseBy100() }}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun TagRow(tag: Tag) {
    var checked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = tag.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                uncheckedColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}