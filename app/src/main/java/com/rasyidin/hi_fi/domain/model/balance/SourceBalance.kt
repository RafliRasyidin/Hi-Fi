package com.rasyidin.hi_fi.domain.model.balance

import android.os.Parcelable
import com.rasyidin.hi_fi.data.source.local.entity.SourceBalanceEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceBalance(
    var sourceId: Int? = null,
    var name: String? = null,
    var balance: Long? = null,
    var updateAt: String? = null,
    var iconPath: Int? = null,
    var bgColor: Int? = null,
) : Parcelable {
    fun toEntity() = SourceBalanceEntity(
        sourceId,
        name,
        balance,
        updateAt,
        iconPath,
        bgColor,
    )
}


