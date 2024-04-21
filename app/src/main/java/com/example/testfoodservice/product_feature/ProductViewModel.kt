package com.example.testfoodservice.product_feature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.catalog_data.use_cases.GetProductByIdUseCase
import com.example.catalog_data.use_cases.IncreaseProductAmountUseCase
import com.example.models.product.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

val emptyProduct = Product(
    id = 0,
    categoryId = 0,
    name = "",
    description = "",
    image = "",
    priceCurrent = 0,
    priceOld = 0,
    measure = 0,
    measureUnit = "",
    energyPer100Grams = 0.0,
    proteinsPer100Grams = 0.0,
    fatsPer100Grams = 0.0,
    carbohydratesPer100Grams = 0.0,
    tagIds = emptyList()
)
@ExperimentalCoroutinesApi
@HiltViewModel
class ProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val increaseProductAmountUseCase: IncreaseProductAmountUseCase
) : BaseViewModel() {

    init {
        getProductById()
    }

    val productId: String? = savedStateHandle["productId"]

    private val _clickedProduct = MutableStateFlow(emptyProduct)
    val clickedProduct: StateFlow<Product>
        get() = _clickedProduct

    private fun getProductById() = viewModelScope.launch(Dispatchers.IO) {
        if (productId != null)
            _clickedProduct.value = getProductByIdUseCase.getProductById(productId.toInt())
    }

    fun addProductToCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        increaseProductAmountUseCase.increaseProductAmount(id)
    }
}