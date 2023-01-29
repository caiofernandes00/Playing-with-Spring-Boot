package com.example.observability.domain.entity

data class User(
    val name: String,
    val age: Int,
    val email: String,
) {
    fun setAge(age: Int): User {
        return this.copy(age = age)
    }

    fun isAdult(): Boolean {
        return age > 17
    }

    fun isTeenager(): Boolean {
        return age in 13..17
    }

    fun isChild(): Boolean {
        return age < 13
    }

}