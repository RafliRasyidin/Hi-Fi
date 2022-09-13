package com.rasyidin.hi_fi.utils

import android.content.Context
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.data.source.local.entity.SourceBalanceEntity

object InitialDataSource {
    fun initSourceBalance(context: Context): List<SourceBalanceEntity> {
        return listOf(
            SourceBalanceEntity(
                0,
                context.getString(R.string.cash),
                0,
                getCurrentDate(DEFAULT_DATE_TIME_FORMAT),
                R.drawable.ic_cash,
                R.color.bg_green
            )
        )
    }
}