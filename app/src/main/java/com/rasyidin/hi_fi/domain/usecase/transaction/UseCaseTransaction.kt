package com.rasyidin.hi_fi.domain.usecase.transaction

data class UseCaseTransaction(
    val addTransaction: AddTransaction,
    val editTransaction: EditTransaction,
    val deleteTransaction: DeleteTransaction,
    val getTransactions: GetTransactions
)
