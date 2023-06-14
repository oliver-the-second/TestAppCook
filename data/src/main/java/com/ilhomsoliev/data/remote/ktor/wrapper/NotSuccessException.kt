package com.ilhomsoliev.data.remote.ktor.wrapper

import io.ktor.utils.io.errors.IOException

class NotSuccessException(
    val status: Status,
    val error: ResponseWrapper.Error? = null
): IOException()