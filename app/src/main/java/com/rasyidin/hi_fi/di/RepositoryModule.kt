package com.rasyidin.hi_fi.di

import com.rasyidin.hi_fi.data.repository.*
import com.rasyidin.hi_fi.data.source.local.SessionManager
import com.rasyidin.hi_fi.data.source.local.db.BalanceDao
import com.rasyidin.hi_fi.data.source.local.db.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesAuthRepository(sessionManager: SessionManager): AuthRepository =
        AuthRepositoryImpl(sessionManager)

    @Provides
    @Singleton
    fun providesBalanceRepository(dao: BalanceDao): BalanceRepository = BalanceRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providesTransactionRepository(dao: TransactionDao): TransactionRepository =
        TransactionRepositoryImpl(dao)
}