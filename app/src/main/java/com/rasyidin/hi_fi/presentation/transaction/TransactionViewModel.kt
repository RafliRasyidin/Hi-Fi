package com.rasyidin.hi_fi.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.idle
import com.rasyidin.hi_fi.domain.model.transaction.Transaction
import com.rasyidin.hi_fi.domain.usecase.transaction.UseCaseTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val useCaseTransaction: UseCaseTransaction) :
    ViewModel() {

    private val _transactions: MutableStateFlow<ResultState<List<Transaction>>> = idle()
    val transaction: StateFlow<ResultState<List<Transaction>>> get() = _transactions

    fun getTransactions() {
        viewModelScope.launch {
            useCaseTransaction.getTransactions().collect { resultState ->
                _transactions.value = resultState
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            useCaseTransaction.addTransaction(transaction)
        }
    }

    fun editTransaction(transaction: Transaction) {
        viewModelScope.launch {
            useCaseTransaction.editTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            useCaseTransaction.deleteTransaction(transaction)
        }
    }
}