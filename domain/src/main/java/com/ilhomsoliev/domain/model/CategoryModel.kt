package com.ilhomsoliev.domain.model

import com.ilhomsoliev.data.remote.dto.category.СategoryDTO

data class CategoryModel(
    val id: Int?=null,
    val image_url: String?=null,
    val name: String?=null
)

fun СategoryDTO.map() = CategoryModel(
    id = id,
    image_url = image_url,
    name = name,
)
