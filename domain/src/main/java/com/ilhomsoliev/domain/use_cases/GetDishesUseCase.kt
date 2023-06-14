package com.ilhomsoliev.domain.use_cases

import com.ilhomsoliev.data.remote.MainRepository
import com.ilhomsoliev.domain.Response
import com.ilhomsoliev.domain.model.DishModel
import com.ilhomsoliev.domain.model.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDishesUseCase(private val repository: MainRepository) {

    suspend operator fun invoke(): Flow<Response<List<DishModel>>> = flow {
        repository.getDishes().on(
            success = {
                it.dishes?.map { it.map() }
                    ?.let { it1 -> Response.Success(it1)?.let { it1 -> emit(it1) } }
            },
            loading = {
                emit(Response.Loading())
            },
            error = {
                emit(Response.Error(it.defaultMessage.toString()))
            }
        )
    }

}