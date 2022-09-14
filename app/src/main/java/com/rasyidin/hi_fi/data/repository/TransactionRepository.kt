package com.rasyidin.hi_fi.data.repository

import com.rasyidin.hi_fi.data.source.local.entity.SourceBalanceAndTransactionRelation
import com.rasyidin.hi_fi.data.source.local.entity.TransactionEntity
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.model.transaction.SourceBalanceAndTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun addTransaction(transactionEntity: TransactionEntity)

    suspend fun editTransaction(transactionEntity: TransactionEntity)

    suspend fun deleteTransaction(transactionEntity: TransactionEntity)

    suspend fun getTransactions(): Flow<ResultState<List<SourceBalanceAndTransactionRelation>>>
}