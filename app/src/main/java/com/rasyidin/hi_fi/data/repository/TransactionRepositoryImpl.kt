package com.rasyidin.hi_fi.data.repository

import com.rasyidin.hi_fi.data.source.local.db.TransactionDao
import com.rasyidin.hi_fi.data.source.local.entity.TransactionEntity
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.fetch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val dao: TransactionDao) :
    TransactionRepository {

    override suspend fun addTransaction(transactionEntity: TransactionEntity) {
        dao.addTransaction(transactionEntity)
    }

    override suspend fun editTransaction(transactionEntity: TransactionEntity) {
        dao.editTransaction(transactionEntity)
    }

    override suspend fun deleteTransaction(transactionEntity: TransactionEntity) {
        dao.deleteTransaction(transactionEntity)
    }

    override suspend fun getTransactions(): Flow<ResultState<List<TransactionEntity>>> {
        return fetch {
            dao.getTransactions()
        }
    }
}