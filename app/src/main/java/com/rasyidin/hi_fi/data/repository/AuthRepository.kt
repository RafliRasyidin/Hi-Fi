package com.rasyidin.hi_fi.data.repository

import com.rasyidin.hi_fi.domain.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun setOnBoardingState(state: Boolean)

    suspend fun getOnBoardingState(): Flow<ResultState<Boolean>>
}