package com.example.testfoodservice.catalog_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testfoodservice.BaseViewModel
import com.example.domain.use_cases.DecreaseProductAmountUseCase
import com.example.domain.use_cases.FilterProductsByCategoriesUseCase
import com.example.domain.use_cases.FilterProductsByTagAndCategoryUseCase
import com.example.domain.use_cases.FilterProductsByTagsUseCase
import com.example.domain.use_cases.GetLocalCategoriesUseCase
import com.example.domain.use_cases.GetLocalProductsUseCase
import com.example.domain.use_cases.GetLocalTagsUseCase
import com.example.domain.use_cases.GetRemoteDataUseCase
import com.example.domain.use_cases.IncreaseProductAmountUseCase
import com.example.domain.models.CategoryModel
import com.example.domain.models.ProductModel
import com.example.domain.models.TagModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CatalogViewModel @Inject constructor(
    localCategoriesUseCase: GetLocalCategoriesUseCase,
    localTagsUseCase: GetLocalTagsUseCase,
    private val localProductsUseCase: GetLocalProductsUseCase,
    private val getRemoteDataUseCase: GetRemoteDataUseCase,
    private val filterProductsByTagsUseCase: FilterProductsByTagsUseCase,
    private val filterProductsByCategoriesUseCase: FilterProductsByCategoriesUseCase,
    private val filterProductsByTagAndCategoryUseCase: FilterProductsByTagAndCategoryUseCase,
    private val increaseProductAmountUseCase: IncreaseProductAmountUseCase,
    private val decreaseProductAmountUseCase: DecreaseProductAmountUseCase
) : ViewModel(), BaseViewModel {

    val categories: StateFlow<List<CategoryModel>> =
        getLocalData(localCategoriesUseCase.categories, viewModelScope)
    val tags: StateFlow<List<TagModel>> = getLocalData(localTagsUseCase.tags, viewModelScope)

    private val _selectedTagIds = MutableStateFlow(listOf<Int>())
    val selectedTagIds: StateFlow<List<Int>> = _selectedTagIds

    private val _selectedCategoryIds = MutableStateFlow(listOf<Int>())
    val selectedCategoryIds: StateFlow<List<Int>> = _selectedCategoryIds

    private val combinedSelectedTagsAndCategories: StateFlow<Pair<List<Int>, List<Int>>> =
        combine(selectedTagIds, selectedCategoryIds) { tags, categories ->
            tags to categories
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(timeout),
            Pair(emptyList(), emptyList())
        )

    val products: StateFlow<List<ProductModel>> = getLocalData(
        combinedSelectedTagsAndCategories.flatMapLatest { (tagIds, categoryIds) ->
            when {
                tagIds.isEmpty() && categoryIds.isEmpty() -> localProductsUseCase.products
                tagIds.isEmpty() -> filterProductsByCategoriesUseCase.filter(categoryIds)
                categoryIds.isEmpty() -> filterProductsByTagsUseCase.filter(tagIds)
                else -> {
                    filterProductsByTagAndCategoryUseCase.filter(
                        tagIds,
                        categoryIds
                    )
                }
            }
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(timeout), 1),
        viewModelScope
    )

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getRemoteDataUseCase.data()
            } catch (e: Exception) {
                //TODO
            }
        }
    }

    fun updateSelectedTags(tagId: Int) = if (_selectedTagIds.value.contains(tagId))
        _selectedTagIds.value -= tagId
    else
        _selectedTagIds.value += tagId

    fun updateSelectedCategories(categoryId: Int) =
        if (_selectedCategoryIds.value.contains(categoryId))
            _selectedCategoryIds.value -= categoryId
        else
            _selectedCategoryIds.value += categoryId

    fun addProductToCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        increaseProductAmountUseCase.increase(id)
    }

    fun removeProductFromCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        decreaseProductAmountUseCase.decrease(id)
    }
}