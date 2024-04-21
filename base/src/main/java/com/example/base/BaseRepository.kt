package com.example.base

import com.example.models.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

abstract class BaseRepository {

    protected fun <E : DataMapper<D>, D> doLocalRequest(
        request: () -> Flow<List<E>>
    ): Flow<List<D>> = request().map { list ->
        list.map {
            it.entityToDto()
        }
    }.catch { exception ->
        throw exception
    }
}