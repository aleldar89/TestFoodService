package com.example.testfoodservice

import com.example.domain.models.BaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

interface BaseViewModel {

    val timeout
        get() = 1000L

    fun getLocalData(
        localDataFlow: Flow<Int>,
        scope: CoroutineScope,
        stopTimeoutMillis: Long = timeout,
        initialValue: Int = 0
    ): StateFlow<Int> = localDataFlow.stateIn(
        scope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis),
        initialValue
    )

    fun <D : BaseModel> getLocalData(
        localDataFlow: Flow<List<D>>,
        scope: CoroutineScope,
        stopTimeoutMillis: Long = timeout,
        initialValue: List<D> = emptyList()
    ): StateFlow<List<D>> = localDataFlow.stateIn(
        scope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis),
        initialValue
    )
}