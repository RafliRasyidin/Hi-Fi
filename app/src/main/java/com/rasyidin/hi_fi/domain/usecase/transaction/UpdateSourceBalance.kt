package com.rasyidin.hi_fi.domain.usecase.transaction

import com.rasyidin.hi_fi.data.repository.BalanceRepository
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance

class UpdateSourceBalance(private val repository: BalanceRepository) {

    suspend operator fun invoke(sourceBalance: SourceBalance) {
        repository.updateSourceBalance(sourceBalance.toEntity())
    }
}