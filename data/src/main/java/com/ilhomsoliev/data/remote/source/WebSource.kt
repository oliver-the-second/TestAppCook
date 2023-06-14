package com.ilhomsoliev.data.remote.source

import com.ilhomsoliev.data.remote.dto.category.CategoriesDTO
import com.ilhomsoliev.data.remote.dto.dish.DishesDTO
import com.ilhomsoliev.data.remote.ktor.KtorSource
import com.ilhomsoliev.data.remote.ktor.wrapper.coroutinesState
import com.ilhomsoliev.data.remote.ktor.wrapper.wrapped

class WebSource : KtorSource() {
    suspend fun getCategories() =
        unauthorizedGet("https://run.mocky.io/v3/058729bd-1402-4578-88de-265481fd7d54").let {
            coroutinesState({ it }) {
                it.wrapped<CategoriesDTO>()
            }
        }

    suspend fun getDishes() =
        unauthorizedGet("https://run.mocky.io/v3/c7a508f2-a904-498a-8539-09d96785446e").let {
            coroutinesState({ it }) {
                it.wrapped<DishesDTO>()
            }
        }
}