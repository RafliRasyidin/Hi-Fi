package com.rasyidin.hi_fi.di

import com.rasyidin.hi_fi.data.repository.AuthRepository
import com.rasyidin.hi_fi.data.repository.BalanceRepository
import com.rasyidin.hi_fi.data.repository.CategoryRepository
import com.rasyidin.hi_fi.data.repository.TransactionRepository
import com.rasyidin.hi_fi.domain.usecase.auth.GetStateOnBoarding
import com.rasyidin.hi_fi.domain.usecase.auth.SetStateOnBoarding
import com.rasyidin.hi_fi.domain.usecase.auth.UseCaseAuth
import com.rasyidin.hi_fi.domain.usecase.balance.*
import com.rasyidin.hi_fi.domain.usecase.balance.UpdateSourceBalance
import com.rasyidin.hi_fi.domain.usecase.home.UseCaseHome
import com.rasyidin.hi_fi.domain.usecase.transaction.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun providesAuthUseCase(authRepository: AuthRepository): UseCaseAuth = UseCaseAuth(
        SetStateOnBoarding(authRepository),
        GetStateOnBoarding(authRepository)
    )

    @Provides
    fun providesBalanceUseCase(balanceRepository: BalanceRepository): UseCaseBalance =
        UseCaseBalance(
            AddSourceBalance(balanceRepository),
            GetSourceBalance(balanceRepository),
            UpdateSourceBalance(balanceRepository),
            DeleteSourceBalance(balanceRepository)
        )

    @Provides
    fun providesTransactionUseCase(
        transactionRepository: TransactionRepository,
        sourceBalanceRepository: BalanceRepository,
        categoryRepository: CategoryRepository
    ): UseCaseTransaction =
        UseCaseTransaction(
            AddTransaction(transactionRepository),
            EditTransaction(transactionRepository),
            DeleteTransaction(transactionRepository),
            GetTransactions(transactionRepository),
            GetSourceBalance(sourceBalanceRepository),
            ValidateTransaction(),
            GetCategoriesByType(categoryRepository),
            GetSourceBalanceById(sourceBalanceRepository),
            com.rasyidin.hi_fi.domain.usecase.transaction.UpdateSourceBalance(sourceBalanceRepository)
        )

    @Provides
    fun providesHomeUseCase(
        transactionRepository: TransactionRepository,
        sourceBalanceRepository: BalanceRepository
    ): UseCaseHome =
        UseCaseHome(
            GetTransactions(transactionRepository),
            GetSourceBalance(sourceBalanceRepository)
        )

}