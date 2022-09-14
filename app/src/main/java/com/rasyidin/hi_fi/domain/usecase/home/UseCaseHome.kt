package com.rasyidin.hi_fi.domain.usecase.home

import com.rasyidin.hi_fi.domain.usecase.balance.GetSourceBalance
import com.rasyidin.hi_fi.domain.usecase.transaction.GetTransactions

data class UseCaseHome(
    val getTransactions: GetTransactions,
    val getSourceBalance: GetSourceBalance,
)
