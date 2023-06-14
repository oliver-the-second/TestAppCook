package com.ilhomsoliev.mainscreen.presentation.categoryDetails

import com.ilhomsoliev.domain.model.DishModel

data class CategoryDetailsState(
    val dishes: List<DishModel> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val isLoading:Boolean = false,
    val activeTagId:Int? = null,
    val activeDishId:Int? = null,
)