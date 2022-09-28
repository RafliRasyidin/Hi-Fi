package com.rasyidin.hi_fi.domain.model.transaction

import com.rasyidin.hi_fi.data.source.local.entity.TransactionEntity
import com.rasyidin.hi_fi.domain.model.category.Category
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.domain.model.category.TransactionCategorize

data class Transaction(
    var id: Int? = null,
    var nominal: Long? = null,
    var date: String? = null,
    var description: String? = null,
    var idTypeTransaction: TransactionCategorize = TransactionCategorize.OUTCOME,
    var categoryId: Int? = null,
    var sourceAccountId: Int? = null,
    var sourceAccountDestinationId: Int? = null
) {
    fun toEntity() = TransactionEntity(
        transactionId = id,
        nominal = nominal,
        date = date,
        description = description,
        idTypeTransaction = idTypeTransaction,
        categoryId = categoryId,
        sourceAccountId = sourceAccountId,
        sourceAccountDestinationId = sourceAccountDestinationId
    )
}

data class SourceBalanceAndTransaction(
    val transaction: Transaction,
    val sourceBalance: SourceBalance? = null,
    val category: Category? = null,
    val sourceBalanceDestination: SourceBalance? = null
)
