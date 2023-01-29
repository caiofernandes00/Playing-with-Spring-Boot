package com.example.observability.infrastructure.api

import com.example.observability.infrastructure.metrics.annotation.CounterMetric
import com.example.observability.usecase.GetUsersListUseCase
import com.example.observability.usecase.output.GetUsersOutput
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class UsersController(
    private val getUsersListUseCase: GetUsersListUseCase
) {

    @GetMapping("/users")
    @CounterMetric("get_users_list")
    fun getUsers(): GetUsersOutput {
        return getUsersListUseCase.getAll()
    }
}