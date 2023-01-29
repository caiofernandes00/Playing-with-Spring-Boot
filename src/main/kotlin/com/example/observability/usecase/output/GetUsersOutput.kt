package com.example.observability.usecase.output

import com.example.observability.domain.entity.User

data class GetUsersOutput(
    val users: List<User>
)