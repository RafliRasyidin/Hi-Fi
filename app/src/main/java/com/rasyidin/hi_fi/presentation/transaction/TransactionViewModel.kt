package com.rasyidin.hi_fi.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.idle
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.domain.model.transaction.SourceBalanceAndTransaction
import com.rasyidin.hi_fi.domain.model.transaction.Transaction
import com.rasyidin.hi_fi.domain.usecase.transaction.UseCaseTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val useCase: UseCaseTransaction) :
    ViewModel() {

    private val _sourceBalance: MutableStateFlow<ResultState<List<SourceBalance>>> = idle()
    val sourceBalance get() = _sourceBalance

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