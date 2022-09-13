package com.rasyidin.hi_fi.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance

@Entity(tableName = "sourceBalance")
data class SourceBalanceEntity(
    @PrimaryKey(true)
    val id: Int? = null,
    val sourceBalanceName: String? = null,
    val balance: Long? = null,
    val updateAt: String? = null,
    val iconPath: Int? = null,
    val bgColor: Int? = null
) {
    fun toDomain() = SourceBalance(
        id,
        sourceBalanceName,
        balance,
        updateAt,
        iconPath,
        bgColor
    )
}
