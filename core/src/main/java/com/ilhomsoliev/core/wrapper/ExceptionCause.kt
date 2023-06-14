package com.ilhomsoliev.core.wrapper

sealed class ExceptionCause(
    val defaultMessage: String? = null,
    val serverMessage: String? = null,
) {
    
    data class IO(
        val message: String,
    ): ExceptionCause(
        serverMessage = message
    )
    
    data class SocketTimeout(
        val message: String,
    ): ExceptionCause(
        serverMessage = message
    )
    
    data class UnknownHost(
        val message: String,
    ): ExceptionCause(
        serverMessage = message
    )
    
    data class RedirectResponse(
        val message: String,
    ): ExceptionCause(
        serverMessage = message
    )
    
    data class ClientRequest(
        val message: String,
    ): ExceptionCause(
        serverMessage = message
    )
    
    data class ServerResponse(
        val message: String,
    ): ExceptionCause(
        serverMessage = message
    )
    
    data class UnknownException(
        val message: String,
    ): ExceptionCause(
        serverMessage = message
    )
}