package com.rasyidin.hi_fi.data.repository

import com.rasyidin.hi_fi.data.source.local.db.BalanceDao
import com.rasyidin.hi_fi.data.source.local.entity.SourceBalanceEntity
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.fetch
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(private val dao: BalanceDao) :
    BalanceRepository {

    override suspend fun addSourceBalance(sourceBalanceEntity: SourceBalanceEntity) {
        dao.addSourceBalance(sourceBalanceEntity)
    }

    override suspend fun updateSourceBalance(sourceBalanceEntity: SourceBalanceEntity) {
        dao.updateSourceBalance(sourceBalanceEntity)
    }

    override suspend fun deleteSourceBalance(sourceBalanceEntity: SourceBalanceEntity) {
        dao.deleteSourceBalance(sourceBalanceEntity)
    }

    override suspend fun getSourcesBalance(): Flow<ResultState<List<SourceBalance>>> {
        return fetch {
            dao.getSourcesBalance().map { entity ->
                entity.toDomain()
            }
        }
    }
}