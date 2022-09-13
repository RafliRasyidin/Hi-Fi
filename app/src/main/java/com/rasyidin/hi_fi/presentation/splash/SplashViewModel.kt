package com.rasyidin.hi_fi.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.idle
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.domain.usecase.auth.UseCaseAuth
import com.rasyidin.hi_fi.domain.usecase.balance.UseCaseBalance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: UseCaseAuth,
    private val useCaseBalance: UseCaseBalance
) : ViewModel() {

    private var _onBoardingState: MutableStateFlow<ResultState<Boolean>> = idle()
    val onBoardingState: StateFlow<ResultState<Boolean>> = _onBoardingState

    private var _sourcesBalance: MutableStateFlow<ResultState<List<SourceBalance>>> = idle()
    val sourcesBalance: StateFlow<ResultState<List<SourceBalance>>> = _sourcesBalance

    init {
        getOnBoardingState()
        getSourcesBalance()
    }

    fun getOnBoardingState() = viewModelScope.launch(Dispatchers.IO) {
        useCase.getStateOnBoarding().collect { state ->
            _onBoardingState.value = state
        }
    }

    private fun getSourcesBalance() {
        viewModelScope.launch {
            useCaseBalance.getSourceBalance().collect { result ->
                _sourcesBalance.value = result
            }
        }
    }
}