package com.rasyidin.hi_fi.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rasyidin.hi_fi.data.source.local.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOutcomeCategories(categoryEntity: List<CategoryEntity>)

    /**
     * Add income category dynamically
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(categoryEntity: CategoryEntity)

    /**
     * Adding list of income category for initial data
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIncomeCategories(incomeCategories: List<CategoryEntity>)

    @Query("SELECT * FROM category WHERE typeCategory = :type")
    suspend fun getCategoriesByType(type: String): List<CategoryEntity>
}