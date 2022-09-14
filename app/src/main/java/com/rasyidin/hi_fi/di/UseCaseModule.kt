package com.rasyidin.hi_fi.di

import com.rasyidin.hi_fi.data.repository.AuthRepository
import com.rasyidin.hi_fi.data.repository.BalanceRepository
import com.rasyidin.hi_fi.data.repository.TransactionRepository
import com.rasyidin.hi_fi.domain.usecase.auth.GetStateOnBoarding
import com.rasyidin.hi_fi.domain.usecase.auth.SetStateOnBoarding
import com.rasyidin.hi_fi.domain.usecase.auth.UseCaseAuth
import com.rasyidin.hi_fi.domain.usecase.balance.*
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
    fun providesAuthUseCase(repository: AuthRepository): UseCaseAuth = UseCaseAuth(
        SetStateOnBoarding(repository),
        GetStateOnBoarding(repository)
    )

    @Provides
    fun providesBalanceUseCase(repository: BalanceRepository): UseCaseBalance =
        UseCaseBalance(
            AddSourceBalance(repository),
            GetSourceBalance(repository),
            UpdateSourceBalance(repository),
            DeleteSourceBalance(repository)
        )

    @Provides
    fun providesTransactionUseCase(repository: TransactionRepository, repositorySourceBalance: BalanceRepository): UseCaseTransaction =
        UseCaseTransaction(
            AddTransaction(repository),
            EditTransaction(repository),
            DeleteTransaction(repository),
            GetTransactions(repository),
            GetSourceBalance(repositorySourceBalance)
        )

    @Provides
    fun providesHomeUseCase(transactionRepository: TransactionRepository, sourceBalanceRepository: BalanceRepository): UseCaseHome =
        UseCaseHome(
            GetTransactions(transactionRepository),
            GetSourceBalance(sourceBalanceRepository)
        )

}