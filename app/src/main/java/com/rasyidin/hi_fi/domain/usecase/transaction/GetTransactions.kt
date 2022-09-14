package com.rasyidin.hi_fi.domain.usecase.transaction

import com.rasyidin.hi_fi.data.repository.TransactionRepository
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.mapResult
import com.rasyidin.hi_fi.domain.model.transaction.SourceBalanceAndTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTransactions(private val repository: TransactionRepository) {

    suspend operator fun invoke(): Flow<ResultState<List<SourceBalanceAndTransaction>>> {
        return repository.getTransactions().map { resultState ->
            mapResult(resultState) {
                this?.map {
                    it.toDomain()
                }
            }
        }
    }
}