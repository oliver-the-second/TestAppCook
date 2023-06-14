package com.ilhomsoliev.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.domain.Response
import com.ilhomsoliev.domain.model.CategoryModel
import com.ilhomsoliev.domain.use_cases.GetCategoriesUseCase
import com.ilhomsoliev.domain.use_cases.GetDishesUseCase
import com.ilhomsoliev.mainscreen.presentation.categoryDetails.CategoryDetailsState
import com.ilhomsoliev.mainscreen.presentation.main.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        MainState()
    )

    init {
        viewModelScope.launch {
            getCategoriesUseCase().onEach { result ->
                when (result) {
                    is Response.Success -> {
                        _state.emit(_state.value.copy(categories = result.data, isLoading = false))
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
}