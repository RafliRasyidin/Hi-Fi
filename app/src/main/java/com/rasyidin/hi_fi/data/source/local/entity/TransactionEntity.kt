package com.rasyidin.hi_fi.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasyidin.hi_fi.domain.model.category.TransactionCategorize
import com.rasyidin.hi_fi.domain.model.transaction.Transaction

@Entity(tableName = "transactionEntity")
data class TransactionEntity(
    @PrimaryKey(true)
    var transactionId: Int? = null,
    var nominal: Long? = null,
    var date: String? = null,
    var description: String? = null,
    var idTypeTransaction: TransactionCategorize = TransactionCategorize.OUTCOME,
    var categoryId: Int? = null,
    var sourceAccountId: Int? = null,
    var sourceAccountDestinationId: Int? = null
) {
    fun toDomain() = Transaction(
        id = transactionId,
        nominal = nominal,
        date = date,
        description = description,
        idTypeTransaction = idTypeTransaction,
        categoryId = categoryId,
        sourceAccountId = sourceAccountId,
        sourceAccountDestinationId = sourceAccountDestinationId
    )
}
