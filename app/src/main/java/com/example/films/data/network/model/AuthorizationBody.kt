package com.example.films.data.network.model

data class AuthorizationBody(
    val login:String,
    val password:String
)

data class AuthorizationResponse(
    val access_token :String,
    val username:String,
    val role:UserRole
)