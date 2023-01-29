package com.example.observability.infrastructure.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class UsersController {

    @GetMapping("/users")
    fun getUsers(): Map<String, String> {
        return mapOf("John" to "Doe")
    }
}