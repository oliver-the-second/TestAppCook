package com.ilhomsoliev.domain.use_cases

import com.ilhomsoliev.data.remote.MainRepository
import com.ilhomsoliev.domain.model.DishModel
import com.ilhomsoliev.domain.model.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class GetDishesLocallyUseCase(private val repository: MainRepository) {
    suspend operator fun invoke(): Flow<List<DishModel>> = flow {
        repository.getDishesLocally().onEach {
            emit(it.map { it.map() })
        }
    }
}