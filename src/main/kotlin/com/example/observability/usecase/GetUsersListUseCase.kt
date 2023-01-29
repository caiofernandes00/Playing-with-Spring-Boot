package com.example.observability.usecase

import com.example.observability.domain.repository.IUserRepository
import com.example.observability.usecase.input.GetUsersByAgeInput
import com.example.observability.usecase.input.GetUsersByAgeRangeInput
import com.example.observability.usecase.input.GetUsersByNameInput
import com.example.observability.usecase.output.GetUsersOutput

class GetUsersListUseCase(
    private val userRepository: IUserRepository
) {

    fun getAll(): GetUsersOutput {
        return GetUsersOutput(userRepository.findAll())
    }

    fun getUsersByName(getUsersByNameInput: GetUsersByNameInput): GetUsersOutput {
        return GetUsersOutput(userRepository.findAll().filter { it.name == getUsersByNameInput.name })
    }

    fun getUsersByAge(getUsersByAgeInput: GetUsersByAgeInput): GetUsersOutput {
        return GetUsersOutput(userRepository.findAll().filter { it.age == getUsersByAgeInput.age })
    }

    fun getUsersByAgeRange(getUsersByAgeRangeInput: GetUsersByAgeRangeInput): GetUsersOutput {
        return GetUsersOutput(
            userRepository.findAll()
                .filter { it.age in getUsersByAgeRangeInput.minAge..getUsersByAgeRangeInput.maxAge }
        )
    }
}