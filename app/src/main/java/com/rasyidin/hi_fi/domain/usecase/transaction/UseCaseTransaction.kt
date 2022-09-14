package com.rasyidin.hi_fi.domain.usecase.transaction

import com.rasyidin.hi_fi.domain.usecase.balance.GetSourceBalance

data class UseCaseTransaction(
    val addTransaction: AddTransaction,
    val editTransaction: EditTransaction,
    val deleteTransaction: DeleteTransaction,
    val getTransactions: GetTransactions,
    val getSourceBalance: GetSourceBalance
)
