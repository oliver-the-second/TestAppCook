package com.ilhomsoliev.basket.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.basket.presentation.BasketState
import com.ilhomsoliev.domain.use_cases.GetDishesLocallyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class BasketViewModel(private val getDishesLocallyUseCase: GetDishesLocallyUseCase) : ViewModel() {

    private val _state = MutableStateFlow(BasketState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BasketState()
    )

    init {
        getDishesLocallyUseCase(viewModelScope).onEach {
            _state.emit(_state.value.copy(dishes = it))
            Log.d("Hello", it.map { it.name }.toString())
        }/*.launchIn(viewModelScope)*/
    }
}