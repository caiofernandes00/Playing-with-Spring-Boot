package com.example.observability.domain.repository

import com.example.observability.domain.entity.User

interface IUserRepository {
    fun findAll(): List<User>
    fun save(user: User): User
    fun update(user: User): User
    fun updateMany(users: List<User>): List<User>
    fun delete(user: User)
}