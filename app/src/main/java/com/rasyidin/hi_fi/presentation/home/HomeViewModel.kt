package com.rasyidin.hi_fi.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.idle
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.domain.model.transaction.SourceBalanceAndTransaction
import com.rasyidin.hi_fi.domain.usecase.home.UseCaseHome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: UseCaseHome) : ViewModel() {

    private var _sourceBalance: MutableStateFlow<ResultState<List<SourceBalance>>> = idle()
    val sourceBalance get() = _sourceBalance.asStateFlow()

    private var _historiesTransaction: MutableStateFlow<ResultState<List<SourceBalanceAndTransaction>>> =
        idle()
    val historiesTransaction get() = _historiesTransaction.asStateFlow()

    fun getSourcesBalance() {
        viewModelScope.launch {
            useCase.getSourceBalance().collect { resultState ->
                _sourceBalance.value = resultState
            }
        }
    }

    fun getHistoriesTransaction() {
        viewModelScope.launch {
            useCase.getTransactions().collect { resultState ->
                _historiesTransaction.value = resultState
            }
        }
    }

}