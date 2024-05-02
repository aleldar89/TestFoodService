package com.example.testfoodservice.catalog_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.category.Category
import com.example.models.product.Product
import com.example.models.tag.Tag
import com.example.testfoodservice.AmountButtons
import com.example.testfoodservice.LoadIcon
import com.example.testfoodservice.LoadImage
import com.example.testfoodservice.R
import com.example.testfoodservice.WideButton
import com.example.testfoodservice.decreaseBy100
import com.example.testfoodservice.ui.theme.Black
import com.example.testfoodservice.ui.theme.Orange
import com.example.testfoodservice.ui.theme.White
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
    val selectedCategoryIds by viewModel.selectedCategoryIds.collectAsStateWithLifecycle()
    val tags by viewModel.tags.collectAsStateWithLifecycle()
    val selectedTagIds by viewModel.selectedTagIds.collectAsStateWithLifecycle()
    val products by viewModel.products.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomAppBar(
                onClickMenu = { scope.launch { sheetState.show() } },
                onClickCart = onNavigateToCart
            )
        },
        bottomBar = {
            if (sheetState.isVisible) {
                BottomSheetContent(
                    tags = tags,
                    selectedTagIds = selectedTagIds,
                    onTagSelected = viewModel::updateSelectedTags,
                    onDismiss = {
                        scope.launch { sheetState.hide() }
                    }
                )
            }
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            CategoryRow(
                categories = categories,
                selectedCategoryIds = selectedCategoryIds,
                onClicked = viewModel::updateSelectedCategories
            )
            CatalogGrid(
                products = products,
                onNavigateToProduct = onNavigateToProduct,
                addProductToCart = viewModel::addProductToCart,
                removeProductFromCart = viewModel::removeProductFromCart
            )
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
            .clickable { onClick(product.id) }
        ) {
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
            AmountButtons(
                amount = product.amount,
                onIncrement = onAddProduct,
                onDecrement = onRemoveProduct
            )
        } else {
            Button(
                onClick = onAddProduct,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .aspectRatio(3f / 1f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_size)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = Black
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Text(
                    text = "${product.priceCurrent.decreaseBy100()} ₽",
                    fontSize = 16.sp,
                )
                if (product.priceOld != null) {
                    Text(
                        text = "${product.priceOld!!.decreaseBy100()} ₽",
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.LineThrough,
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
    selectedCategoryIds: List<Int>,
    onClicked: (categoryId: Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(categories.size) { index ->
            CategoryButton(
                category = categories[index],
                buttonState = selectedCategoryIds.contains(categories[index].id),
                onClicked = onClicked
            )
        }
    }
}

@Composable
fun CategoryButton(
    category: Category,
    buttonState: Boolean,
    onClicked: (categoryId: Int) -> Unit
) {

    Button(
        onClick = {
            onClicked(category.id)
        },
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_size)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (buttonState) Orange else White,
            disabledContentColor = Gray
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
                    color = if (buttonState) White else Black
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    tags: List<Tag>,
    selectedTagIds: List<Int>,
    onTagSelected: (tagId: Int) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        content = {
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
                tags.forEach { tag ->
                    TagRow(
                        tag = tag,
                        checked = selectedTagIds.contains(tag.id),
                        onCheckedChange = { tagId ->
                            onTagSelected(tagId)
                        }
                    )
                }

                WideButton(
                    onClick = onDismiss,
                    text = "Готово"
                )
            }
        }
    )
}

@Composable
fun TagRow(
    tag: Tag,
    checked: Boolean,
    onCheckedChange: (tagId: Int) -> Unit
) {

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
                onCheckedChange(tag.id)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                uncheckedColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}