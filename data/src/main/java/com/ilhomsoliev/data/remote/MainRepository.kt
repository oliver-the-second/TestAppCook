package com.ilhomsoliev.data.remote

import com.ilhomsoliev.core.wrapper.DataStateTest
import com.ilhomsoliev.data.local.model.DishEntity
import com.ilhomsoliev.data.remote.dto.category.CategoriesDTO
import com.ilhomsoliev.data.remote.dto.dish.DishesDTO
import com.ilhomsoliev.data.remote.source.WebSource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getCategories(): DataStateTest<CategoriesDTO>
    suspend fun getDishes():DataStateTest<DishesDTO>
    suspend fun insertDish(dishEntity: DishEntity)
    suspend fun deleteDish(dishEntity: DishEntity)
    suspend fun getDishesLocally(): Flow<List<DishEntity>>

}