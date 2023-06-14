package com.ilhomsoliev.mainscreen.presentation.categoryDetails

import com.ilhomsoliev.domain.model.DishModel

sealed class CategoryDetailsEvent {
    data class OnTagClick(val tagId: Int): CategoryDetailsEvent()
    data class OnDishClick(val dishId: Int): CategoryDetailsEvent()
    data class OnAddToBasketClick(val dish: DishModel): CategoryDetailsEvent()
}