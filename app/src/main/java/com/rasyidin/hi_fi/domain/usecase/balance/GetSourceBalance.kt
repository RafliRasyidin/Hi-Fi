package com.rasyidin.hi_fi.domain.usecase.balance

import com.rasyidin.hi_fi.data.repository.BalanceRepository
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import kotlinx.coroutines.flow.Flow

class GetSourceBalance(private val repository: BalanceRepository) {

    suspend operator fun invoke(): Flow<ResultState<List<SourceBalance>>> {
        return repository.getSourcesBalance()
    }
}