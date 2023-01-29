package com.example.observability.usecase.input

data class GetUsersByNameInput(
    val name: String
)

data class GetUsersByAgeInput(
    val age: Int
)

data class GetUsersByAgeRangeInput(
    val minAge: Int,
    val maxAge: Int
)