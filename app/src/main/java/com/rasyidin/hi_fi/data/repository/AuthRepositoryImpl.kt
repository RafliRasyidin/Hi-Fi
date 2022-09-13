package com.rasyidin.hi_fi.data.repository

import com.rasyidin.hi_fi.data.source.local.SessionManager
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.fetch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val sessionManager: SessionManager) :
    AuthRepository {

    override suspend fun setOnBoardingState(state: Boolean) {
        sessionManager.setOnBoardingState(state)
    }

    override suspend fun getOnBoardingState(): Flow<ResultState<Boolean>> {
        return fetch {
            sessionManager.getOnBoardingState().first()
        }
    }
}