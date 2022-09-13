package com.rasyidin.hi_fi.domain.model.transaction

import com.rasyidin.hi_fi.data.source.local.entity.TransactionEntity
import com.rasyidin.hi_fi.domain.model.balance.TransactionCategorize
import java.sql.Date

data class Transaction(
    var id: Int? = null,
    var nominal: Long? = null,
    var date: String? = null,
    var description: String? = null,
    var idTypeTransaction: TransactionCategorize = TransactionCategorize.OUTCOME,
    var categoryId: Int? = null,
    var sourceAccount: String? = null,
    var destinationAccount: String? = null
) {
    fun toEntity() = TransactionEntity(
        id = id,
        nominal = nominal,
        date = date,
        description = description,
        idTypeTransaction = idTypeTransaction,
        categoryId = categoryId,
        sourceAccount = sourceAccount,
        destinationAccount = destinationAccount
    )
}
