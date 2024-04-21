package com.example.testfoodservice.catalog_feature

import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.catalog_data.use_cases.DecreaseProductAmountUseCase
import com.example.catalog_data.use_cases.FilterProductsByCategoriesUseCase
import com.example.catalog_data.use_cases.FilterProductsByTagsUseCase
import com.example.catalog_data.use_cases.GetLocalCategoriesUseCase
import com.example.catalog_data.use_cases.GetLocalProductsUseCase
import com.example.catalog_data.use_cases.GetLocalTagsUseCase
import com.example.catalog_data.use_cases.GetRemoteDataUseCase
import com.example.catalog_data.use_cases.IncreaseProductAmountUseCase
import com.example.models.category.Category
import com.example.models.product.Product
import com.example.models.tag.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CatalogViewModel @Inject constructor(
    localCategoriesUseCase: GetLocalCategoriesUseCase,
    localTagsUseCase: GetLocalTagsUseCase,
    localProductsUseCase: GetLocalProductsUseCase,
    private val getRemoteDataUseCase: GetRemoteDataUseCase,
    private val filterProductsByTagsUseCase: FilterProductsByTagsUseCase,
    private val filterProductsByCategoriesUseCase: FilterProductsByCategoriesUseCase,
    private val increaseProductAmountUseCase: IncreaseProductAmountUseCase,
    private val decreaseProductAmountUseCase: DecreaseProductAmountUseCase
) : BaseViewModel() {

    val categories: StateFlow<List<Category>> = getLocalData(localCategoriesUseCase.localCategories)
    val tags: StateFlow<List<Tag>> = getLocalData(localTagsUseCase.localTags)

    private val _selectedTags = MutableStateFlow(listOf<Int>())
    private val selectedTags: StateFlow<List<Int>>
        get() = _selectedTags

    private val _selectedCategories = MutableStateFlow(listOf<Int>())
    private val selectedCategories: StateFlow<List<Int>>
        get() = _selectedCategories

    val products: StateFlow<List<Product>> = selectedCategories.flatMapLatest { list ->
        if (list.isEmpty())
            localProductsUseCase.localProducts
        else
            filterProductsByCategoriesUseCase.filterProductsByCategories(list)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000L), emptyList())

    init {
        getData()
    }

    private fun getData() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                getRemoteDataUseCase.getRemoteData()
            }
        } catch (e: Exception) {
            //TODO error toast from SingleLiveEvent
        }
    }

    fun updateSelectedTags(tagId: Int) =
        if (_selectedTags.value.contains(tagId))
            _selectedTags.value -= tagId
        else
            _selectedTags.value += tagId

    fun updateSelectedCategories(categoryId: Int) =
        if (_selectedCategories.value.contains(categoryId))
            _selectedCategories.value -= categoryId
        else
            _selectedCategories.value += categoryId

    private fun filterProductsByTags(tagIds: List<Int>): StateFlow<List<Product>> =
        getLocalData(filterProductsByTagsUseCase.filterProductsByTags(tagIds))

    private fun filterProductsByCategories(categoryIds: List<Int>): StateFlow<List<Product>> =
        getLocalData(filterProductsByCategoriesUseCase.filterProductsByCategories(categoryIds))

    fun addProductToCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        increaseProductAmountUseCase.increaseProductAmount(id)
    }

    fun removeProductFromCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        decreaseProductAmountUseCase.decreaseProductAmount(id)
    }
}