package com.rasyidin.hi_fi.data.repository

import com.rasyidin.hi_fi.data.source.local.entity.SourceBalanceEntity
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {

    suspend fun addSourceBalance(sourceBalanceEntity: SourceBalanceEntity)

    suspend fun updateSourceBalance(sourceBalanceEntity: SourceBalanceEntity)

    suspend fun deleteSourceBalance(sourceBalanceEntity: SourceBalanceEntity)

    suspend fun getSourcesBalance(): Flow<ResultState<List<SourceBalance>>>

    suspend fun getSourceBalanceById(sourceBalanceId: Int): Flow<ResultState<SourceBalanceEntity>>

}