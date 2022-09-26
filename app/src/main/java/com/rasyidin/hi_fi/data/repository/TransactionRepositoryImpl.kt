package com.rasyidin.hi_fi.data.repository

import com.rasyidin.hi_fi.data.source.local.db.TransactionDao
import com.rasyidin.hi_fi.data.source.local.entity.SourceBalanceAndTransactionRelation
import com.rasyidin.hi_fi.data.source.local.entity.TransactionEntity
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.fetch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun addTransaction(transactionEntity: TransactionEntity) {
        transactionDao.addTransaction(transactionEntity)
    }

    override suspend fun editTransaction(transactionEntity: TransactionEntity) {
        transactionDao.editTransaction(transactionEntity)
    }

    override suspend fun deleteTransaction(transactionEntity: TransactionEntity) {
        transactionDao.deleteTransaction(transactionEntity)
    }

    override suspend fun getTransactions(): Flow<ResultState<List<SourceBalanceAndTransactionRelation>>> {
        return fetch {
            transactionDao.getTransactions()
        }
    }
}