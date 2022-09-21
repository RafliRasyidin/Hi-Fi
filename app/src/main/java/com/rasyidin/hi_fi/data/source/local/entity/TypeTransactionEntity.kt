package com.rasyidin.hi_fi.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasyidin.hi_fi.domain.model.category.TransactionCategorize

@Entity(tableName = "typeTransaction")
data class TypeTransactionEntity(
    @PrimaryKey
    var id: TransactionCategorize = TransactionCategorize.OUTCOME,
    var name: String
)
