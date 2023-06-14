package com.ilhomsoliev.data.remote.dto.dish

data class DishDTO(
    val description: String? = null,
    val id: Int? = null,
    val image_url: String? = null,
    val name: String? = null,
    val price: Int? = null,
    val tegs: List<String>? = null,
    val weight: Int? = null,
)