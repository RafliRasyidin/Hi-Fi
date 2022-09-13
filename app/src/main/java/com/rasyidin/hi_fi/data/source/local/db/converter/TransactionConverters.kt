package com.rasyidin.hi_fi.data.source.local.db.converter

import androidx.room.TypeConverter
import com.rasyidin.hi_fi.domain.model.balance.TransactionCategorize
import com.rasyidin.hi_fi.domain.model.balance.TransactionCategorize.*
import com.rasyidin.hi_fi.domain.model.transaction.Transaction

class TransactionConverters {

    @TypeConverter
    fun toTransactionCategorize(value: Int): TransactionCategorize {
        return when (value) {
            1 -> OUTCOME
            2 -> INCOME
            else -> TRANSFER
        }
    }

    @TypeConverter
    fun fromTransactionCategorize(transactionCategorize: TransactionCategorize): Int {
        return when (transactionCategorize) {
            OUTCOME -> 1
            INCOME -> 2
            TRANSFER -> 3
        }
    }
}