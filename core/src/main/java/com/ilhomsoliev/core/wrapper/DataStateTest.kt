package com.ilhomsoliev.core.wrapper

sealed class DataStateTest<out T: Any> {
    
    data class Success<out T: Any>(
        val data: T,
    ): DataStateTest<T>()
    
    data class Loading<out T: Any>(
        val type: Type = Type.MEDIUM,
    ): DataStateTest<T>() {
        
        enum class Type {
            @Suppress("unused")
            SHORT,
            MEDIUM,
            @Suppress("unused")
            LONG
        }
    }
    
    data class Error<out T: Any>(
        val cause: ExceptionCause,
    ): DataStateTest<T>()
    
    val isSuccess: Boolean
        get() = this is Success
    
    val isError: Boolean
        get() = this is Error
    
    val isLoading: Boolean
        get() = this is Loading
    
    inline fun <E: Any?> on(
        success: (T) -> E,
        loading: (Loading.Type) -> E,
        error: (ExceptionCause) -> E,
    ): E {
        return when(this) {
            is Success -> success(data)
            is Loading -> loading(type)
            is Error -> error(cause)
        }
    }
    
    fun <E: Any?> on(
        success: E,
        loading: E,
        error: E,
    ): E = on({ success }, { loading }, { error })
    
    inline fun <E: Any?> onSuccess(
        block: (T) -> E,
    ): E? = if(this is Success) block(data) else null
    
    inline fun <E: Any?> onLoading(
        block: (Loading.Type) -> E,
    ): E? = if(this is Loading) block(type) else null
    
    inline fun <E: Any?> onError(
        block: (ExceptionCause) -> E,
    ): E? = if(this is Error) {
        block(cause)
    } else {
        null
    }
}