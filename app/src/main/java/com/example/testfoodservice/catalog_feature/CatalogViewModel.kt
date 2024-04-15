package com.example.testfoodservice.catalog_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models_api.FeedItem
import com.example.models_api.category.Category
import com.example.models_api.product.Product
import com.example.models_api.tag.Tag
import com.example.catalog_data.use_cases.GetLocalCategoriesUseCase
import com.example.catalog_data.use_cases.GetLocalProductsUseCase
import com.example.catalog_data.use_cases.GetLocalTagsUseCase
import com.example.catalog_data.use_cases.GetRemoteDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CatalogViewModel @Inject constructor(
    localCategoriesUseCase: GetLocalCategoriesUseCase,
    localTagsUseCase: GetLocalTagsUseCase,
    localProductsUseCase: GetLocalProductsUseCase,
    private val getRemoteDataUseCase: GetRemoteDataUseCase
) : ViewModel() {

    init {
        getData()
    }

    val categories: StateFlow<List<Category>> =
        getLocalData(localCategoriesUseCase.getLocalCategories)

    val tags: StateFlow<List<Tag>> = getLocalData(localTagsUseCase.getLocalTags)

    val products: StateFlow<List<Product>> = getLocalData(localProductsUseCase.getLocalProducts)

    private fun getData() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                getRemoteDataUseCase.getRemoteData()
            }
        } catch (e: Exception) {
            //TODO error toast from SingleLiveEvent
            throw e
        }
    }

    private fun <D : FeedItem> getLocalData(
        localDataFlow: Flow<List<D>>,
        scope: CoroutineScope = viewModelScope,
        stopTimeoutMillis: Long = 3000L,
        initialValue: List<D> = emptyList()
    ) = localDataFlow.stateIn(
        scope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis),
        initialValue
    )
}