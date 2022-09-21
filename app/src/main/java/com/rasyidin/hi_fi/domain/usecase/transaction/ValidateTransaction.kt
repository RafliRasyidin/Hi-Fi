package com.rasyidin.hi_fi.domain.usecase.transaction

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ValidateTransaction {

    private var isCategoryChosen = false
    private var isNominalNotEmpty = false
    private var isSourceBalanceChosen = false

    fun isValidated(): Flow<Boolean> {
        return flow {
            emit(isCategoryChosen && isNominalNotEmpty && isSourceBalanceChosen)
        }
    }

    fun setCategoryState(isCategoryChosen: Boolean) {
        this.isCategoryChosen = isCategoryChosen
    }

    fun setNominalState(isNominalNotEmpty: Boolean) {
        this.isNominalNotEmpty = isNominalNotEmpty
    }

    fun setSourceBalanceState(isSourceBalanceChosen: Boolean) {
        this.isSourceBalanceChosen = isSourceBalanceChosen
    }

    enum class TransactionState {
        CATEGORY,
        NOMINAL,
        SOURCE_BALANCE
    }
}