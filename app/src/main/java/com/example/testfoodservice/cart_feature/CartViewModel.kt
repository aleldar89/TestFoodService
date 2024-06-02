package com.example.testfoodservice.cart_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testfoodservice.BaseViewModel
import com.example.domain.use_cases.DecreaseProductAmountUseCase
import com.example.domain.use_cases.GetCartPriceUseCase
import com.example.domain.use_cases.GetProductsFromCartUseCase
import com.example.domain.use_cases.IncreaseProductAmountUseCase
import com.example.domain.models.ProductModel
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
    cartPriceUseCase: GetCartPriceUseCase,
    private val increaseProductAmountUseCase: IncreaseProductAmountUseCase,
    private val decreaseProductAmountUseCase: DecreaseProductAmountUseCase
) : ViewModel(), BaseViewModel {

    val productsFromCart: StateFlow<List<ProductModel>> =
        getLocalData(productsFromCartUseCase.cart, viewModelScope)

    val cartPrice: StateFlow<Int> = getLocalData(cartPriceUseCase.price, viewModelScope)

    fun addProductToCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        increaseProductAmountUseCase.increase(id)
    }

    fun removeProductFromCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        decreaseProductAmountUseCase.decrease(id)
    }
}