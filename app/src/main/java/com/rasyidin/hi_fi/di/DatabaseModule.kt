package com.rasyidin.hi_fi.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rasyidin.hi_fi.data.source.local.SessionManager
import com.rasyidin.hi_fi.data.source.local.db.BalanceDao
import com.rasyidin.hi_fi.data.source.local.db.HiFiDatabase
import com.rasyidin.hi_fi.utils.InitialDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    private val coroutineScope = CoroutineScope(SupervisorJob())

    private suspend fun populateDatabase(context: Context, dao: Provider<BalanceDao>) {
        val financeDao = dao.get()
        financeDao.addSourceBalance(InitialDataSource.initSourceBalance(context).first())
    }

    @Provides
    @Singleton
    fun providesSessionManager(@ApplicationContext context: Context) = SessionManager(context)

    @Provides
    @Singleton
    fun providesHiFiDatabase(@ApplicationContext context: Context, dao: Provider<BalanceDao>) =
        Room.databaseBuilder(context, HiFiDatabase::class.java, "hi_fi")
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    coroutineScope.launch(Dispatchers.IO) {
                        populateDatabase(context, dao)
                    }
                }

                /*override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    val financeDao = dao.get()
                    coroutineScope.launch(Dispatchers.IO) {
                        val count = financeDao.getSourcesBalance().size
                        Log.d("DatabaseCount", count.toString())
                        if (count == 0) {
                            populateDatabase(context, dao)
                        }
                    }
                }*/
            })
            .build()

    @Provides
    @Singleton
    fun providesFinanceDao(database: HiFiDatabase) = database.financeDao()

    @Provides
    @Singleton
    fun providesTransactionDao(database: HiFiDatabase) = database.transactionDao()
}