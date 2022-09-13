package com.rasyidin.hi_fi.domain.usecase.auth

import com.rasyidin.hi_fi.data.repository.AuthRepository

class SetStateOnBoarding(private val repository: AuthRepository) {

    suspend operator fun invoke(state: Boolean) {
        repository.setOnBoardingState(state)
    }

}