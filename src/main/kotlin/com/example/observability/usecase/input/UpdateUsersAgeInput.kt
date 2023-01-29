package com.example.observability.usecase.input

import com.example.observability.domain.entity.User

data class UpdateUsersAgeInput(
    val users: List<User>
)