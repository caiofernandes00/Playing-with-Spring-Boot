package com.example.observability.usecase

import com.example.observability.domain.repository.IUserRepository
import com.example.observability.domain.service.UsersService
import com.example.observability.usecase.input.UpdateUsersAgeInput
import org.springframework.stereotype.Service

@Service
class UpdateUsersAgeUseCase(
    private val usersService: UsersService,
    private val userRepository: IUserRepository
) {

    fun increase(updateUsersAgeInput: UpdateUsersAgeInput) {
        val users = userRepository.findAll()
        val usersToUpdate = updateUsersAgeInput.users.filter { user -> users.any { it.name == user.name } }

        usersService.increaseAge(usersToUpdate)
        userRepository.updateMany(usersToUpdate)
    }

    fun decrease(updateUsersAgeInput: UpdateUsersAgeInput) {
        usersService.decreaseAge(updateUsersAgeInput.users)
    }
}