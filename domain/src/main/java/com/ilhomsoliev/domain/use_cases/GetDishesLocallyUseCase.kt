package com.ilhomsoliev.domain.use_cases

import android.util.Log
import com.ilhomsoliev.data.remote.MainRepository
import com.ilhomsoliev.domain.model.DishModel
import com.ilhomsoliev.domain.model.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class GetDishesLocallyUseCase(private val repository: MainRepository) {
    operator fun invoke(scope:CoroutineScope): Flow<List<DishModel>> = flow {
        repository.getDishesLocally().first().map { it.map() }/*.map {
            emit(it.map { it.map() })
        }.launchIn(scope)*//*.onEach {
            Log.d("Hello UserCase", it.map { it.name }.toString())
        }*/
    }
}