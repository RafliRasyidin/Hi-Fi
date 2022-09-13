package com.rasyidin.hi_fi.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasyidin.hi_fi.domain.model.balance.TransactionCategorize
import com.rasyidin.hi_fi.domain.model.transaction.Transaction
import java.sql.Date

@Entity(tableName = "transactionEntity")
data class TransactionEntity(
    @PrimaryKey(true)
    var id: Int? = null,
    var nominal: Long? = null,
    var date: String? = null,
    var description: String? = null,
    var idTypeTransaction: TransactionCategorize = TransactionCategorize.OUTCOME,
    var categoryId: Int? = null,
    var sourceAccount: String? = null,
    var destinationAccount: String? = null
) {
    fun toDomain() = Transaction(
        id = id,
        nominal = nominal,
        date = date,
        description = description,
        idTypeTransaction = idTypeTransaction,
        categoryId = categoryId
    )
}
