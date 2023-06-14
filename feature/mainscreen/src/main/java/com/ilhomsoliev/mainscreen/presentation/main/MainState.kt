package com.ilhomsoliev.mainscreen.presentation.main

import com.ilhomsoliev.domain.model.CategoryModel

data class MainState(
    val categories: List<CategoryModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)