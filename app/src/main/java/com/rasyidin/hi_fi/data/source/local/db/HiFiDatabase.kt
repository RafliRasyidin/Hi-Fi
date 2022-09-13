package com.rasyidin.hi_fi.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rasyidin.hi_fi.data.source.local.db.converter.DateConverters
import com.rasyidin.hi_fi.data.source.local.db.converter.TransactionConverters
import com.rasyidin.hi_fi.data.source.local.entity.*

@Database(
    entities = [
        IncomeEntity::class,
        OutcomeEntity::class,
        SourceBalanceEntity::class,
        TransactionEntity::class,
        TypeTransactionEntity::class
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(TransactionConverters::class, DateConverters::class)
abstract class HiFiDatabase : RoomDatabase() {
    abstract fun financeDao(): BalanceDao
    abstract fun transactionDao(): TransactionDao
}