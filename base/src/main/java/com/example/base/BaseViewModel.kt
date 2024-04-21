package com.example.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.FeedItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel : ViewModel() {

    protected fun <D : FeedItem> getLocalData(
        localDataFlow: Flow<List<D>>,
        scope: CoroutineScope = viewModelScope,
        stopTimeoutMillis: Long = 3000L,
        initialValue: List<D> = emptyList()
    ): StateFlow<List<D>> = localDataFlow.stateIn(
        scope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis),
        initialValue
    )
}