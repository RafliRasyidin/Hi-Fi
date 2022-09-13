package com.rasyidin.hi_fi.presentation.on_boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.hi_fi.domain.usecase.auth.UseCaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val useCase: UseCaseAuth) : ViewModel() {

    fun setOnBoardingState(state: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        useCase.setStateOnBoarding(state)
    }
}