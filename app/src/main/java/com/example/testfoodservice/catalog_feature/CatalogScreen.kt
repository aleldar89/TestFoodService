package com.example.testfoodservice.catalog_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.category.Category
import com.example.models.product.Product
import com.example.models.tag.Tag
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel(),
    onNavigateToCart: () -> Unit = {},
    onNavigateToProduct: (id: Int) -> Unit = {}
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
            CategoryRow(
                categories = categories,
                onClicked = viewModel::updateSelectedCategories
            )
            CatalogGrid(
                products = products,
                onNavigateToProduct = onNavigateToProduct,
                addProductToCart = viewModel::addProductToCart,
                removeProductFromCart = viewModel::removeProductFromCart
            )
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
                        TagRow(tag = tag) {
                            viewModel.updateSelectedTags(tag.id)
                        }
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
fun CatalogGrid(
    products: List<Product>,
    onNavigateToProduct: (id: Int) -> Unit,
    addProductToCart: (id: Int) -> Unit,
    removeProductFromCart: (id: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products.size) { index ->
            CatalogItem(
                product = products[index],
                onClick = onNavigateToProduct,
                onAddProduct = { addProductToCart(products[index].id) },
                onRemoveProduct = { removeProductFromCart(products[index].id) }
            )
        }
    }
}

@Composable
fun CatalogItem(
    product: Product,
    onClick: (id: Int) -> Unit,
    onAddProduct: () -> Unit,
    onRemoveProduct: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(product.id) }) {
            LoadImage(modifier = Modifier.fillMaxSize())
            if (product.priceOld != null)
                LoadIcon()
        }

        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = product.name, maxLines = 1)
            Text(text = "${product.measure} ${product.measureUnit}")
        }

        if (product.amount > 0) {
            CounterRow(
                amount = product.amount,
                onIncrement = onAddProduct,
                onDecrement = onRemoveProduct
            )
        } else {
            Button(
                onClick = onAddProduct,
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
}

@Composable
fun CategoryRow(
    categories: List<Category>,
    onClicked: (categoryId: Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(categories.size) { index ->
            CategoryButton(
                category = categories[index],
                onClicked = onClicked
            )
        }
    }
}

@Composable
fun CategoryButton(
    category: Category,
    onClicked: (categoryId: Int) -> Unit
) {
    val buttonState = rememberSaveable { mutableStateOf(false) }

    Button(
        onClick = {
            buttonState.value = !buttonState.value
            onClicked(category.id)
        },
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (buttonState.value) Color(0xFFFFA500) else Color.White, // Orange color when pressed
            disabledContentColor = Color.Gray
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        content = {
            Row(
                modifier = Modifier.background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.name,
                    fontSize = 16.sp,
                    color = if (buttonState.value) Color.White else Color.Black
                )
            }
        }
    )
}

@Composable
fun TagRow(
    tag: Tag,
    onClicked: (tagId: Int) -> Unit
) {
    var checked by rememberSaveable { mutableStateOf(false) }

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
            onCheckedChange = {
                checked = it
                onClicked(tag.id)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                uncheckedColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}