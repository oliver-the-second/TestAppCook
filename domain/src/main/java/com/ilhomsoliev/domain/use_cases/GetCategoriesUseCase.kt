package com.ilhomsoliev.domain.use_cases

import android.util.Log
import com.ilhomsoliev.data.remote.MainRepository
import com.ilhomsoliev.domain.Response
import com.ilhomsoliev.domain.model.CategoryModel
import com.ilhomsoliev.domain.model.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCase(private val repository: MainRepository) {

    suspend operator fun invoke(): Flow<Response<List<CategoryModel>>> = flow {
        try {

            repository.getCategories().on(
                success = {
                    it.сategories?.map { it.map() }
                        ?.let { it1 -> Response.Success(it1)?.let { it1 -> emit(it1) } }
                },
                loading = {
                    emit(Response.Loading())
                },
                error = {
                    emit(Response.Error(it.defaultMessage.toString()))
                }
            )
        }catch (e:Exception){
            emit(Response.Error(e.message.toString()))
        }
    }
}