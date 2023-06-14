package com.ilhomsoliev.domain.use_cases

import com.ilhomsoliev.data.remote.MainRepository
import com.ilhomsoliev.domain.model.DishModel
import com.ilhomsoliev.domain.model.map

class DeleteDishUseCase(private val repository: MainRepository) {
    suspend operator fun invoke(dishModel: DishModel) {
        repository.deleteDish(dishModel.map())
    }
}