package com.rasyidin.hi_fi.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "outcome")
data class OutcomeEntity(
    @PrimaryKey(true)
    val id: Int,
    val balance: Long,
    val updatedAt: String,
)
