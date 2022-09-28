package com.example.films.utils.extensions

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun<reified T : Any> T.encodeToString():String{
    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
    return json.encodeToString(this)
}

inline fun<reified T> String.decodeFromString():T{
    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
    return json.decodeFromString(this)
}