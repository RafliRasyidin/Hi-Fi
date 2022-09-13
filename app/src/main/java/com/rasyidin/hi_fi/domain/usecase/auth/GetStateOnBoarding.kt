package com.rasyidin.hi_fi.domain.usecase.auth

import com.rasyidin.hi_fi.data.repository.AuthRepository
import com.rasyidin.hi_fi.domain.ResultState
import kotlinx.coroutines.flow.Flow

class GetStateOnBoarding(private val repository: AuthRepository) {

    suspend operator fun invoke(): Flow<ResultState<Boolean>> {
        return repository.getOnBoardingState()
    }
}