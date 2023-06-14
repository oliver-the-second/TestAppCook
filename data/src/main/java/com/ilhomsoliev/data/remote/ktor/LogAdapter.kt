package com.ilhomsoliev.data.remote.ktor

import android.util.Log
import io.ktor.client.plugins.logging.Logger

object LogAdapter: Logger {
    override fun log(message: String) {
        Log.d("KTOR", message)
    }
}