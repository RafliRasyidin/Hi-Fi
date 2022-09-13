package com.rasyidin.hi_fi.domain.usecase.balance

import com.rasyidin.hi_fi.data.repository.BalanceRepository
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance

class AddSourceBalance(private val repository: BalanceRepository) {

    suspend operator fun invoke(sourceBalance: SourceBalance) {
        repository.addSourceBalance(sourceBalance.toEntity())
    }

}