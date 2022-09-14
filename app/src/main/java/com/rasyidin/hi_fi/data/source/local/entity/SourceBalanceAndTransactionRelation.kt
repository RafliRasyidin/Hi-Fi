package com.rasyidin.hi_fi.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.rasyidin.hi_fi.domain.model.transaction.SourceBalanceAndTransaction

data class SourceBalanceAndTransactionRelation(
    @Embedded
    val transactions: TransactionEntity,

    @Relation(
        parentColumn = "sId",
        entityColumn = "sourceId"
    )
    val sourceBalance: SourceBalanceEntity,
) {
    fun toDomain() : SourceBalanceAndTransaction {
        return SourceBalanceAndTransaction(
            sourceBalance = this.sourceBalance.toDomain(),
            transaction = this.transactions.toDomain()
        )
    }
}