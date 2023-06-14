package com.ilhomsoliev.domain.model

import com.ilhomsoliev.data.local.model.DishEntity
import com.ilhomsoliev.data.remote.dto.dish.DishDTO

data class DishModel(
    val description: String? = null,
    val id: Int? = null,
    val image_url: String? = null,
    val name: String? = null,
    val price: Int? = null,
    val tegs: List<String>? = null,
    val weight: Int? = null,
)

fun DishDTO.map() = DishModel(
    description = description,
    id = id,
    image_url = image_url,
    name = name,
    price = price,
    tegs = tegs,
    weight = weight,
)

fun DishEntity.map() = DishModel(
    description = description,
    id = id,
    image_url = image_url,
    name = name,
    price = price,
    weight = weight,
)

fun DishModel.map() = DishEntity(
    description = description?:"",
    id = id?:0,
    image_url = image_url?:"",
    name = name?:"",
    price = price?:0,
    weight = weight?:0,
)