package com.example.testfoodservice.catalog_feature

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.catalog_data.use_cases.DecreaseProductAmountUseCase
import com.example.catalog_data.use_cases.FilterProductsByCategoriesUseCase
import com.example.catalog_data.use_cases.FilterProductsByTagAndCategoryUseCase
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
) : BaseViewModel() {

    val categories: StateFlow<List<Category>> = getLocalData(localCategoriesUseCase.localCategories)
    val tags: StateFlow<List<Tag>> = getLocalData(localTagsUseCase.localTags)

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

    val products: StateFlow<List<Product>> = getLocalData(
        combinedSelectedTagsAndCategories.flatMapLatest { (tagIds, categoryIds) ->
            when {
                tagIds.isEmpty() && categoryIds.isEmpty() -> localProductsUseCase.localProducts

                tagIds.isEmpty() -> {
                    filterProductsByCategoriesUseCase.filterProductsByCategories(categoryIds)
                }

                categoryIds.isEmpty() -> {
                    filterProductsByTagsUseCase.filterProductsByTags(tagIds)
                }

                else -> {
                    filterProductsByTagAndCategoryUseCase.filterProductsByTagAndCategory(
                        tagIds,
                        categoryIds
                    )
                }
            }
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(timeout), 1)
    )

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getRemoteDataUseCase.getRemoteData()
            } catch (e: Exception) {
                //TODO
            }
        }
    }

    fun updateSelectedTags(tagId: Int) =
        if (_selectedTagIds.value.contains(tagId))
            _selectedTagIds.value -= tagId
        else
            _selectedTagIds.value += tagId

    fun updateSelectedCategories(categoryId: Int) =
        if (_selectedCategoryIds.value.contains(categoryId))
            _selectedCategoryIds.value -= categoryId
        else
            _selectedCategoryIds.value += categoryId

    fun addProductToCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        increaseProductAmountUseCase.increaseProductAmount(id)
    }

    fun removeProductFromCart(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        decreaseProductAmountUseCase.decreaseProductAmount(id)
    }
}