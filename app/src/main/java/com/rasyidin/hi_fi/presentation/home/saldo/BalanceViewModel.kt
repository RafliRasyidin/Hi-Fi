package com.rasyidin.hi_fi.presentation.home.saldo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.idle
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.domain.usecase.balance.UseCaseBalance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(private val useCaseBalance: UseCaseBalance) : ViewModel() {

    private var _sourcesBalance: MutableStateFlow<ResultState<List<SourceBalance>>> = idle()
    val sourcesBalance: StateFlow<ResultState<List<SourceBalance>>> = _sourcesBalance

    fun getSourcesBalance() {
        viewModelScope.launch {
            useCaseBalance.getSourceBalance().collect { result ->
                _sourcesBalance.value = result
            }
        }
    }

    fun updateSourceBalance(sourceBalance: SourceBalance) {
        viewModelScope.launch {
            useCaseBalance.updateSourceBalance(sourceBalance)
        }
    }

    fun addSourceBalance(sourceBalance: SourceBalance) {
        viewModelScope.launch {
            useCaseBalance.addSourceBalance(sourceBalance)
        }
    }

    fun deleteSourceBalance(sourceBalance: SourceBalance) {
        viewModelScope.launch {
            useCaseBalance.deleteSourceBalance(sourceBalance)
        }
    }
}