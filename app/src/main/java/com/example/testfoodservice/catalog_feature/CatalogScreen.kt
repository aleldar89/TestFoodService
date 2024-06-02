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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.CategoryModel
import com.example.domain.models.ProductModel
import com.example.domain.models.TagModel
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
                    tagModels = tags,
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
                productModels = products,
                onNavigateToProduct = onNavigateToProduct,
                addProductToCart = viewModel::addProductToCart,
                removeProductFromCart = viewModel::removeProductFromCart
            )
        }
    }
}

@Composable
fun CatalogGrid(
    productModels: List<ProductModel>,
    onNavigateToProduct: (id: Int) -> Unit,
    addProductToCart: (id: Int) -> Unit,
    removeProductFromCart: (id: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_8))
    ) {
        items(productModels.size) { index ->
            CatalogItem(
                productModel = productModels[index],
                onClick = onNavigateToProduct,
                onAddProduct = { addProductToCart(productModels[index].id) },
                onRemoveProduct = { removeProductFromCart(productModels[index].id) }
            )
        }
    }
}

@Composable
fun CatalogItem(
    productModel: ProductModel,
    onClick: (id: Int) -> Unit,
    onAddProduct: () -> Unit,
    onRemoveProduct: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_8))
            .fillMaxWidth(),
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(productModel.id) }
        ) {
            LoadImage(modifier = Modifier.fillMaxSize())
            if (productModel.priceOld != null)
                LoadIcon()
        }

        Column(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_8))
        ) {
            Text(text = productModel.name, maxLines = 1)
            Text(text = "${productModel.measure} ${productModel.measureUnit}")
        }

        if (productModel.amount > 0) {
            AmountButtons(
                amount = productModel.amount,
                onIncrement = onAddProduct,
                onDecrement = onRemoveProduct
            )
        } else {
            Button(
                onClick = onAddProduct,
                modifier = Modifier
                    .aspectRatio(3f / 1f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_size)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = Black
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = dimensionResource(R.dimen.elevation_10)
                )
            ) {
                Text(
                    text = "${productModel.priceCurrent.decreaseBy100()} ₽",
                    fontSize = 16.sp,
                )
                if (productModel.priceOld != null) {
                    Text(
                        text = "${productModel.priceOld!!.decreaseBy100()} ₽",
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
    categories: List<CategoryModel>,
    selectedCategoryIds: List<Int>,
    onClicked: (categoryId: Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_8))
    ) {
        items(categories.size) { index ->
            CategoryButton(
                categoryModel = categories[index],
                buttonState = selectedCategoryIds.contains(categories[index].id),
                onClicked = onClicked
            )
        }
    }
}

@Composable
fun CategoryButton(
    categoryModel: CategoryModel,
    buttonState: Boolean,
    onClicked: (categoryId: Int) -> Unit
) {

    Button(
        onClick = {
            onClicked(categoryModel.id)
        },
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_size)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (buttonState) Orange else White,
            disabledContentColor = Gray
        ),
        contentPadding = PaddingValues(
            horizontal = dimensionResource(R.dimen.padding_16),
            vertical = dimensionResource(R.dimen.padding_8)
        ),
        content = {
            Row(
                modifier = Modifier.background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = categoryModel.name,
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
    tagModels: List<TagModel>,
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
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_16),
                        vertical = dimensionResource(R.dimen.padding_8)
                    )
            ) {
                Text(
                    text = "Подобрать блюда",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_8))
                )
                tagModels.forEach { tag ->
                    TagRow(
                        tagModel = tag,
                        checked = selectedTagIds.contains(tag.id),
                        onCheckedChange = { tagId ->
                            onTagSelected(tagId)
                        }
                    )
                }

                WideButton(
                    onClick = onDismiss,
                    text = stringResource(R.string.ready)
                )
            }
        }
    )
}

@Composable
fun TagRow(
    tagModel: TagModel,
    checked: Boolean,
    onCheckedChange: (tagId: Int) -> Unit
) {

    Row(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_16),
                vertical = dimensionResource(R.dimen.padding_8)
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = tagModel.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange(tagModel.id)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                uncheckedColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}