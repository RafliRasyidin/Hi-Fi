package com.rasyidin.hi_fi.presentation.home

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
class HomeViewModel @Inject constructor(private val useCaseBalance: UseCaseBalance) : ViewModel() {

    private var _sourceBalance: MutableStateFlow<ResultState<List<SourceBalance>>> = idle()
    val sourceBalance: StateFlow<ResultState<List<SourceBalance>>> get() = _sourceBalance

    fun getSourcesBalance() {
        viewModelScope.launch {
            useCaseBalance.getSourceBalance().collect { resultState ->
                _sourceBalance.value = resultState
            }
        }
    }

}