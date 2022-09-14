package com.rasyidin.hi_fi.data.source.local.db

import androidx.room.*
import com.rasyidin.hi_fi.data.source.local.entity.SourceBalanceAndTransactionRelation
import com.rasyidin.hi_fi.data.source.local.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(transactionEntity: TransactionEntity)

    @Update
    suspend fun editTransaction(transactionEntity: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transactionEntity: TransactionEntity)

    @Transaction
    @Query("SELECT * FROM transactionEntity")
    suspend fun getTransactions(): List<SourceBalanceAndTransactionRelation>
}