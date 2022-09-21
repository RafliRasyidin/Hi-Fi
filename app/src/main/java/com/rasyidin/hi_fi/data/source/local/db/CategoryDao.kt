package com.rasyidin.hi_fi.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rasyidin.hi_fi.data.source.local.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategories(categoryEntity: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category WHERE typeCategory = :type")
    suspend fun getCategoriesByType(type: String): List<CategoryEntity>
}