package com.ilhomsoliev.domain.repository

import com.ilhomsoliev.core.wrapper.DataStateTest
import com.ilhomsoliev.data.local.dao.DishDao
import com.ilhomsoliev.data.local.model.DishEntity
import com.ilhomsoliev.data.remote.MainRepository
import com.ilhomsoliev.data.remote.dto.category.CategoriesDTO
import com.ilhomsoliev.data.remote.dto.dish.DishesDTO
import com.ilhomsoliev.data.remote.source.WebSource
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(private val webSource: WebSource, private val dishDao: DishDao) :
    MainRepository {

    override suspend fun getCategories(): DataStateTest<CategoriesDTO> = webSource.getCategories()

    override suspend fun getDishes(): DataStateTest<DishesDTO> = webSource.getDishes()

    override suspend fun insertDish(
        dish: DishEntity
    ) {
        dishDao.insert(dish)
    }

    override suspend fun deleteDish(
        dish: DishEntity
    ) {
        dishDao.delete(dish)
    }

    override fun getDishesLocally(): Flow<List<DishEntity>> = dishDao.getDishes()


}