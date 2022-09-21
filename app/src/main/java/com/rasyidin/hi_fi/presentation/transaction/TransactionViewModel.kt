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
import com.rasyidin.hi_fi.domain.usecase.transaction.ValidateTransaction.TransactionState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val useCase: UseCaseTransaction) :
    ViewModel() {

    private var _sourceBalance: MutableStateFlow<ResultState<List<SourceBalance>>> = idle()
    val sourceBalance get() = _sourceBalance.asStateFlow()

    private var _isValidated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isValidated get() = _isValidated.asStateFlow()

    private var _categoriesByType: Channel<ResultState<List<Category>>> = Channel()
    val categoriesByType get() = _categoriesByType.receiveAsFlow()

    fun getCategoriesByType(type: String) {
        viewModelScope.launch {
            useCase.getCategoriesByType(type).collect { result ->
                _categoriesByType.send(result)
            }
        }
    }

    fun setButtonState(transactionState: ValidateTransaction.TransactionState, isValid: Boolean) {
        when (transactionState) {
            CATEGORY -> {
                useCase.validateTransaction.setCategoryState(isValid)
                getTransactionButtonState()
            }
            NOMINAL -> {
                useCase.validateTransaction.setNominalState(isValid)
                getTransactionButtonState()
            }
            SOURCE_BALANCE -> {
                useCase.validateTransaction.setSourceBalanceState(isValid)
                getTransactionButtonState()
            }
        }
    }

    private fun getTransactionButtonState() {
        viewModelScope.launch {
            useCase.validateTransaction.isValidated()
                .collect { isValidated ->
                    _isValidated.value = isValidated
                }
        }
    }

    fun getSourceBalance() {
        viewModelScope.launch {
            useCase.getSourceBalance().collect { resultState ->
                _sourceBalance.value = resultState
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