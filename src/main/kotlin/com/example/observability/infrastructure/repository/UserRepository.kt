package com.example.observability.infrastructure.repository

import com.example.observability.domain.entity.User
import com.example.observability.domain.repository.IUserRepository
import org.springframework.stereotype.Repository


@Repository
class UserRepository : IUserRepository {
    override fun findAll(): List<User> {
        return listOf(User("John", 30, "johndoe.@example.com"))
    }

    override fun save(user: User): User {
        return user
    }

    override fun update(user: User): User {
        return user
    }

    override fun updateMany(users: List<User>): List<User> {
        return users
    }

    override fun delete(user: User) {
        // do nothing
    }
}