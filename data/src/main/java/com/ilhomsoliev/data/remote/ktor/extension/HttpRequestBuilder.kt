package com.ilhomsoliev.data.remote.ktor.extension

import io.ktor.http.URLBuilder

fun URLBuilder.query(
    vararg pairs: Pair<String, String>
) = pairs.forEach { parameters.append(it.first, it.second) }