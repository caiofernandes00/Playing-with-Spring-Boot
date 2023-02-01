package com.example.observability.infrastructure.api

import com.example.observability.infrastructure.metrics.annotation.CounterMetric
import com.example.observability.infrastructure.metrics.annotation.ElapsedTimeMetric
import com.example.observability.usecase.GetUsersListUseCase
import com.example.observability.usecase.output.GetUsersOutput
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
class UsersController(
    private val getUsersListUseCase: GetUsersListUseCase
) {

    @RequestMapping(path = ["/users"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    @CounterMetric(name = "get_users_list_counter", description = "Get users list", tags = ["tag1", "tag2"])
    @ElapsedTimeMetric(name = "get_users_list_timer", description = "Get users list", tags = ["tag1", "tag2"])
    fun getUsers(): GetUsersOutput = getUsersListUseCase.getAll()
}