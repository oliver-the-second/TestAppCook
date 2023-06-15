package com.ilhomsoliev.basket.presentation

import com.ilhomsoliev.domain.model.DishModel

data class BasketState(
    val isLoading:Boolean = false,
    val dishes:List<DishModel> = emptyList()
)