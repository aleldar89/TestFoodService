package com.example.testfoodservice.product_feature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testfoodservice.BaseViewModel
import com.example.domain.use_cases.GetProductByIdUseCase
import com.example.domain.use_cases.IncreaseProductAmountUseCase
import com.example.domain.models.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

val emptyProductModel = ProductModel(
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
) : ViewModel(), BaseViewModel {

    init {
        getProductById()
    }

    private val productId: String? = savedStateHandle["productId"]

    private val _clickedProduct = MutableStateFlow(emptyProductModel)
    val clickedProductModel: StateFlow<ProductModel> = _clickedProduct

    private fun getProductById() = viewModelScope.launch(Dispatchers.IO) {
        if (productId != null)
            _clickedProduct.value = getProductByIdUseCase.product(productId.toInt())
    }

    fun addProductToCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        increaseProductAmountUseCase.increase(id)
    }
}