package com.example.testfoodservice.cart_feature

import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.catalog_data.use_cases.DecreaseProductAmountUseCase
import com.example.catalog_data.use_cases.GetProductsFromCartUseCase
import com.example.catalog_data.use_cases.IncreaseProductAmountUseCase
import com.example.models.product.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CartViewModel @Inject constructor(
    productsFromCartUseCase: GetProductsFromCartUseCase,
    private val increaseProductAmountUseCase: IncreaseProductAmountUseCase,
    private val decreaseProductAmountUseCase: DecreaseProductAmountUseCase
) : BaseViewModel() {

    val productsFromCart: StateFlow<List<Product>> =
        getLocalData(productsFromCartUseCase.productsFromCart)

    fun addProductToCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        increaseProductAmountUseCase.increaseProductAmount(id)
    }

    fun removeProductFromCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        decreaseProductAmountUseCase.decreaseProductAmount(id)
    }
}