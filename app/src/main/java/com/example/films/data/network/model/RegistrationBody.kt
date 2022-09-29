package com.example.films.data.network.model

data class RegistrationBody(
    val username:String,
    val login :String,
    val password:String
)

data class RegistrationResponse(
    val error:String
)