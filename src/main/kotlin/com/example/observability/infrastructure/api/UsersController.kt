package com.example.observability.infrastructure.api

import com.example.observability.infrastructure.metrics.annotation.CounterMetric
import com.example.observability.infrastructure.metrics.annotation.TimerMetric
import com.example.observability.usecase.GetUsersListUseCase
import com.example.observability.usecase.output.GetUsersOutput
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class UsersController(
    private val getUsersListUseCase: GetUsersListUseCase
) {

    @GetMapping("/users")
    @CounterMetric(name = "get_users_list_counter", description = "Get users list", tags = ["tag1", "tag2"])
    @TimerMetric(name = "get_users_list_timer", description = "Get users list", tags = ["tag1", "tag2"])
    fun getUsers(): GetUsersOutput {
        return getUsersListUseCase.getAll()
    }
}