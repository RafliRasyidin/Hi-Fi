package com.rasyidin.hi_fi.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.idle
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.domain.model.category.Category
import com.rasyidin.hi_fi.domain.model.transaction.Transaction
import com.rasyidin.hi_fi.domain.usecase.transaction.UseCaseTransaction
import com.rasyidin.hi_fi.domain.usecase.transaction.ValidateTransaction
import com.rasyidin.hi_fi.domain.usecase.transaction.ValidateTransaction.TransactionPickState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val useCase: UseCaseTransaction) :
    ViewModel() {

    private var _sourceBalances: MutableStateFlow<ResultState<List<SourceBalance>>> = idle()
    val sourceBalances get() = _sourceBalances.asStateFlow()

    private var _isValidated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isValidated get() = _isValidated.asStateFlow()

    private var _categoriesByType: Channel<ResultState<List<Category>>> = Channel()
    val categoriesByType get() = _categoriesByType.receiveAsFlow()

    private var _sourceBalance: MutableStateFlow<ResultState<SourceBalance>> = idle()
    val sourceBalance get() = _sourceBalance.asStateFlow()

    private var _sourceBalanceFrom: MutableStateFlow<ResultState<SourceBalance>> = idle()
    val sourceBalanceFrom get() = _sourceBalanceFrom.asStateFlow()

    private var _sourceBalanceTo: MutableStateFlow<ResultState<SourceBalance>> = idle()
    val sourceBalanceTo get() = _sourceBalanceTo.asStateFlow()

    fun getSourceBalanceByIdFrom(sourceBalanceId: Int) {
        viewModelScope.launch {
            useCase.getSourceBalanceById(sourceBalanceId).collect { result ->
                _sourceBalanceFrom.value = result
            }
        }
    }

    fun getSourceBalanceByIdTo(sourceBalanceId: Int) {
        viewModelScope.launch {
            useCase.getSourceBalanceById(sourceBalanceId).collect { result ->
                _sourceBalanceTo.value = result
            }
        }
    }

    fun getSourceBalanceById(sourceBalanceId: Int) {
        viewModelScope.launch {
            useCase.getSourceBalanceById(sourceBalanceId).collect { result ->
                _sourceBalance.value = result
            }
        }
    }

    fun updateSourceBalance(sourceBalance: SourceBalance) {
        viewModelScope.launch {
            useCase.updateSourceBalance(sourceBalance)
        }
    }

    fun getCategoriesByType(type: String) {
        viewModelScope.launch {
            useCase.getCategoriesByType(type).collect { result ->
                _categoriesByType.send(result)
            }
        }
    }

    fun setButtonState(
        transactionPickState: ValidateTransaction.TransactionPickState,
        isValid: Boolean,
        transactionType: ValidateTransaction.TransactionType = ValidateTransaction.TransactionType.OUTCOME
    ) {
        when (transactionPickState) {
            CATEGORY -> {
                useCase.validateTransaction.setCategoryState(isValid)
                getTransactionButtonState(transactionType)
            }
            NOMINAL -> {
                useCase.validateTransaction.setNominalState(isValid)
                getTransactionButtonState(transactionType)
            }
            SOURCE_BALANCE -> {
                useCase.validateTransaction.setSourceBalanceState(isValid)
                getTransactionButtonState(transactionType)
            }
            SOURCE_BALANCE_FROM -> {
                useCase.validateTransaction.setSourceBalanceFromState(isValid)
                getTransactionButtonState(ValidateTransaction.TransactionType.TRANSFER)
            }
            SOURCE_BALANCE_TO -> {
                useCase.validateTransaction.setSourceBalanceToState(isValid)
                getTransactionButtonState(ValidateTransaction.TransactionType.TRANSFER)
            }
        }
    }

    private fun getTransactionButtonState(transactionType: ValidateTransaction.TransactionType = ValidateTransaction.TransactionType.OUTCOME) {
        viewModelScope.launch {
            useCase.validateTransaction.isValidated(transactionType)
                .collect { isValidated ->
                    _isValidated.value = isValidated
                }
        }
    }

    fun getSourceBalance() {
        viewModelScope.launch {
            useCase.getSourceBalance().collect { resultState ->
                _sourceBalances.value = resultState
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            useCase.addTransaction(transaction)
        }
    }

    fun editTransaction(transaction: Transaction) {
        viewModelScope.launch {
            useCase.editTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            useCase.deleteTransaction(transaction)
        }
    }
}