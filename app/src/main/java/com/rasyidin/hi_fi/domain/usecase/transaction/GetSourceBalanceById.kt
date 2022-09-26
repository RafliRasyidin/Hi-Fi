package com.rasyidin.hi_fi.domain.usecase.transaction

import com.rasyidin.hi_fi.data.repository.BalanceRepository
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.mapResult
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSourceBalanceById(private val repository: BalanceRepository) {

    suspend operator fun invoke(sourceBalanceId: Int): Flow<ResultState<SourceBalance>> {
        return repository.getSourceBalanceById(sourceBalanceId).map { result ->
            mapResult(result) {
                this?.toDomain()
            }
        }
    }
}