package com.example.observability.domain.service

import com.example.observability.domain.entity.User
import org.springframework.stereotype.Service

@Service
class UsersService {

    fun increaseAge(user: List<User>): List<User> {
        return user.map { it.setAge(it.age + 1) }
    }

    fun decreaseAge(user: List<User>): List<User> {
        return user.map { it.setAge(it.age - 1) }
    }
}