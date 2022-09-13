package com.rasyidin.hi_fi.data.source.local.db

import androidx.room.*
import com.rasyidin.hi_fi.data.source.local.entity.SourceBalanceEntity

@Dao
interface BalanceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSourceBalance(sourceBalanceEntity: SourceBalanceEntity)

    @Update
    suspend fun updateSourceBalance(sourceBalanceEntity: SourceBalanceEntity)

    @Delete
    suspend fun deleteSourceBalance(sourceBalanceEntity: SourceBalanceEntity)

    @Query("SELECT * FROM sourceBalance")
    suspend fun getSourcesBalance(): List<SourceBalanceEntity>
}