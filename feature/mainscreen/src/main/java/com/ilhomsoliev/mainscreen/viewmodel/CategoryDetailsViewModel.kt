package com.ilhomsoliev.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.domain.Response
import com.ilhomsoliev.domain.use_cases.GetDishesUseCase
import com.ilhomsoliev.domain.use_cases.InsertDishUseCase
import com.ilhomsoliev.mainscreen.presentation.categoryDetails.CategoryDetailsEvent
import com.ilhomsoliev.mainscreen.presentation.categoryDetails.CategoryDetailsState
import com.ilhomsoliev.mainscreen.presentation.categoryDetails.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

class CategoryDetailsViewModel(
    private val getDishesUseCase: GetDishesUseCase,
    private val insertDishUseCase: InsertDishUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryDetailsState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CategoryDetailsState()
    )

    init {
        viewModelScope.launch {
            getDishesUseCase().onEach { result ->
                when (result) {
                    is Response.Success -> {
                        val tags =  result.data.map {
                            it.tegs?.map { it } ?: emptyList()
                        }.flatten().unique().map { Tag(Random.nextInt(1, 1000000), it, false) }

                        _state.emit(
                            _state.value.copy(
                                dishes = result.data,
                                isLoading = false,
                                tags = tags,
                                activeTagId = tags[0].id,
                                activeDishId = null,
                            )
                        )
                    }

                    is Response.Loading -> {
                        _state.emit(_state.value.copy(isLoading = true))
                    }

                    is Response.Error -> {
                        _state.emit(_state.value.copy(isLoading = false))
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: CategoryDetailsEvent) {
        when (event) {
            is CategoryDetailsEvent.OnTagClick -> {
                viewModelScope.launch {
                    _state.emit(
                        _state.value.copy(activeTagId = event.tagId)
                    )
                }
            }

            is CategoryDetailsEvent.OnDishClick -> {
                viewModelScope.launch {
                    _state.emit(
                        _state.value.copy(activeDishId = event.dishId)
                    )
                }
            }

            is CategoryDetailsEvent.OnAddToBasketClick -> {
                viewModelScope.launch {
                    insertDishUseCase(event.dish)
                }
            }
        }
    }
}

fun <T> List<T>.unique() = this.distinctBy { it }

fun <T> List<List<T>>.flatten(): List<T> {
    val result = mutableListOf<T>()
    for (list in this) {
        result.addAll(list)
    }
    return result
}