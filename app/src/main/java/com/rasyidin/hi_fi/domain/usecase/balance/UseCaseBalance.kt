package com.rasyidin.hi_fi.domain.usecase.balance

data class UseCaseBalance(
    val addSourceBalance: AddSourceBalance,
    val getSourceBalance: GetSourceBalance,
    val updateSourceBalance: UpdateSourceBalance,
    val deleteSourceBalance: DeleteSourceBalance
)
