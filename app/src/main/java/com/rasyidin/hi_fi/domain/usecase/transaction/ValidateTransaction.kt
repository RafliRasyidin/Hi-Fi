package com.rasyidin.hi_fi.domain.usecase.transaction

import com.rasyidin.hi_fi.domain.usecase.transaction.ValidateTransaction.TransactionType.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ValidateTransaction {

    private var isCategoryChosen = false
    private var isNominalNotEmpty = false
    private var isSourceBalanceChosen = false

    private var isSourceBalanceFromChosen = false
    private var isSourceBalanceToChosen = false

    fun isValidated(transactionType: TransactionType = OUTCOME): Flow<Boolean> {
        return flow {
            when (transactionType) {
                OUTCOME, INCOME -> emit(isCategoryChosen && isNominalNotEmpty && isSourceBalanceChosen)
                TRANSFER -> emit(isSourceBalanceFromChosen && isSourceBalanceToChosen && isNominalNotEmpty)
            }
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

    fun setSourceBalanceFromState(isSourceBalanceFromChosen: Boolean) {
        this.isSourceBalanceFromChosen = isSourceBalanceFromChosen
    }

    fun setSourceBalanceToState(isSourceBalanceToChosen: Boolean) {
        this.isSourceBalanceToChosen = isSourceBalanceToChosen
    }

    enum class TransactionType {
        OUTCOME,
        INCOME,
        TRANSFER
    }

    enum class TransactionPickState {
        CATEGORY,
        NOMINAL,
        SOURCE_BALANCE,
        SOURCE_BALANCE_FROM,
        SOURCE_BALANCE_TO
    }
}