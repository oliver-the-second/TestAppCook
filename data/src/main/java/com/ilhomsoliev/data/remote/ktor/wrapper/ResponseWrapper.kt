package com.ilhomsoliev.data.remote.ktor.wrapper

import com.fasterxml.jackson.annotation.JsonAlias
import com.ilhomsoliev.core.wrapper.DataStateTest
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.*
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

enum class Status {
    @JsonAlias("success")
    SUCCESS,
    
    @JsonAlias("error")
    @Suppress("unused")
    ERROR
}

data class ResponseWrapperTest<T: Any?>(
    val status: Status? = null,
    val data: T,
    val error: Error? = null,
    val paginator: Paginator? = null,
) {
    
    @Suppress("unused")
    class Paginator(
        val type: String,
        val total: Int,
        val perPage: Int,
        val currentPage: Int,
        val last_page: Int,
    ) {
        
        constructor(): this(
            (""), (0), (0),
            (0), (0)
        )
    }
    
    data class Error(
        val code: String? = null,
        val message: String? = null,
        val exception: String? = null,
        val file: String? = null,
        val line: Int? = null,
        val trace: Any? = null,
    )
}

data class ResponseWrapper<T: Any?>(
    val status: Status,
    val data: T,
    val error: Error? = null,
    val paginator: Paginator? = null,
) {
    
    @Suppress("unused")
    class Paginator(
        val type: String,
        val total: Int,
        val perPage: Int,
        val currentPage: Int,
        val last_page: Int,
    ) {
        
        constructor(): this(
            (""), (0), (0),
            (0), (0)
        )
    }
    
    data class Error(
        val code: String? = null,
        val message: String? = null,
        val exception: String? = null,
        val file: String? = null,
        val line: Int? = null,
        val trace: Any? = null,
    )
    
    val dataChecked: T
        get() {
            if(status != Status.SUCCESS)
                throw NotSuccessException(status)
            return data
        }
}

data class ErrorResponseWrapper(
    val status: String = "",
    val error: Error = Error(),
) {
    
    data class Error(
        val code: String = "",
        val message: String = "",
        val exception: String = "",
        val file: String = "",
        val line: Int = 0,
        val trace: Any? = null,
    )
}

suspend inline fun <reified T> HttpResponse.paginateWrapped(
): Pair<T, ResponseWrapper.Paginator> where T: Any? {
    val response = body<ResponseWrapper<T>>()
    return Pair(response.data, response.paginator!!)
}

suspend inline fun HttpResponse.errorWrapped() =
    body<ErrorResponseWrapper>()

// По неизвестным причинам не приходит status
@Suppress("unused")
suspend inline fun <reified T> HttpResponse.wrappedTest(): T
        where T: Any? = body<ResponseWrapperTest<T>>().data

suspend inline fun <reified T> HttpResponse.wrapped(): T
        where T: Any? = body<T>()

private fun String?.errorController(): String {
    return this ?: "Неизвестная ошибка"
}

/**
 * [coroutinesState] превращает запрос в DataState (suspend)
 */
suspend fun <T: Any> coroutinesState(
    request: () -> HttpResponse,
    expectCode: Int = 200,
    response: suspend () -> T,
) = try {
    request().let {
        if(it.status.value == expectCode)
            withContext(Dispatchers.IO) {
                DataStateTest.Success(
                    data = response(),
                )
            }
        else DataStateTest.Error(
            cause = com.ilhomsoliev.core.wrapper.ExceptionCause.UnknownException(
                message = it.errorWrapped().error
                    .message.errorController()
            ),
        )
    }
} catch(e: IOException) {
    DataStateTest.Error(
        cause = com.ilhomsoliev.core.wrapper.ExceptionCause.IO(
            message = e.message.errorController()
        ),
    )
} catch(e: SocketTimeoutException) {
    DataStateTest.Error(
        cause = com.ilhomsoliev.core.wrapper.ExceptionCause.SocketTimeout(
            message = e.message.errorController()
        ),
    )
} catch(e: UnknownHostException) {
    DataStateTest.Error(
        cause = com.ilhomsoliev.core.wrapper.ExceptionCause.UnknownHost(
            message = e.message.errorController()
        ),
    )
} catch(e: ResponseException) {
    when(e) {
        is RedirectResponseException -> {
            DataStateTest.Error(
                cause = com.ilhomsoliev.core.wrapper.ExceptionCause.RedirectResponse(
                    message = e.message.errorController()
                ),
            )
        }
        is ClientRequestException -> {
            DataStateTest.Error(
                cause = com.ilhomsoliev.core.wrapper.ExceptionCause.ClientRequest(
                    message = e.message.errorController()
                ),
            )
        }
        is ServerResponseException -> {
            DataStateTest.Error(
                cause = com.ilhomsoliev.core.wrapper.ExceptionCause.ServerResponse(
                    message = e.message.errorController()
                ),
            )
        }
        else -> {
            DataStateTest.Error(
                cause = com.ilhomsoliev.core.wrapper.ExceptionCause.UnknownException(
                    message = e.message.errorController()
                ),
            )
        }
    }
} catch(e: Exception) {
    DataStateTest.Error(
        cause = com.ilhomsoliev.core.wrapper.ExceptionCause.UnknownException(
            message = e.message.errorController()
        ),
    )
}

/**
 * [flowState] превращает запрос в DataState (flow)
 */
@Suppress("unused")
fun <T: Any> flowState(
    block: suspend () -> T,
) = flow {
    emit(DataStateTest.Loading())
    val response = try {
        DataStateTest.Success(
            data = block(),
        )
    } catch(e: IOException) {
        DataStateTest.Error(
            cause = com.ilhomsoliev.core.wrapper.ExceptionCause.IO(
                message = e.message.errorController()
            ),
        )
    } catch(e: SocketTimeoutException) {
        DataStateTest.Error(
            cause = com.ilhomsoliev.core.wrapper.ExceptionCause.SocketTimeout(
                message = e.message.errorController()
            ),
        )
    } catch(e: UnknownHostException) {
        DataStateTest.Error(
            cause = com.ilhomsoliev.core.wrapper.ExceptionCause.UnknownHost(
                message = e.message.errorController()
            ),
        )
    } catch(e: ResponseException) {
        when(e) {
            is RedirectResponseException -> {
                DataStateTest.Error(
                    cause = com.ilhomsoliev.core.wrapper.ExceptionCause.RedirectResponse(
                        message = e.message.errorController()
                    ),
                )
            }
            is ClientRequestException -> {
                DataStateTest.Error(
                    cause = com.ilhomsoliev.core.wrapper.ExceptionCause.ClientRequest(
                        message = e.message.errorController()
                    ),
                )
            }
            is ServerResponseException -> {
                DataStateTest.Error(
                    cause = com.ilhomsoliev.core.wrapper.ExceptionCause.ServerResponse(
                        message = e.message.errorController()
                    ),
                )
            }
            else -> {
                DataStateTest.Error(
                    cause = com.ilhomsoliev.core.wrapper.ExceptionCause.UnknownException(
                        message = e.message.errorController()
                    ),
                )
            }
        }
    } catch(e: Exception) {
        DataStateTest.Error(
            cause = com.ilhomsoliev.core.wrapper.ExceptionCause.UnknownException(
                message = e.message.errorController()
            ),
        )
    }
    emit(response)
}.flowOn(Dispatchers.IO)
