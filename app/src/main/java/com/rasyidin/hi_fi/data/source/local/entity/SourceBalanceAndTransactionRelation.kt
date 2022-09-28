package com.rasyidin.hi_fi.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.rasyidin.hi_fi.domain.model.transaction.SourceBalanceAndTransaction

data class SourceBalanceAndTransactionRelation(
    @Embedded
    val transactions: TransactionEntity,

    @Relation(
        parentColumn = "sourceAccountId",
        entityColumn = "sourceId"
    )
    val sourceBalance: SourceBalanceEntity? = null,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val category: CategoryEntity? = null,

    @Relation(
        parentColumn = "sourceAccountDestinationId",
        entityColumn = "sourceId"
    )
    val sourceBalanceDestination: SourceBalanceEntity? = null,
) {
    fun toDomain() : SourceBalanceAndTransaction {
        return SourceBalanceAndTransaction(
            sourceBalance = this.sourceBalance?.toDomain(),
            transaction = this.transactions.toDomain(),
            category = this.category?.toDomain(),
            sourceBalanceDestination = this.sourceBalanceDestination?.toDomain()
        )
    }
}