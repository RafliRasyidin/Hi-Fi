package com.rasyidin.hi_fi.data.repository

import com.rasyidin.hi_fi.data.source.local.entity.CategoryEntity
import com.rasyidin.hi_fi.domain.ResultState
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun addCategory(categoryEntity: CategoryEntity)

    suspend fun getCategoriesByType(type: String): Flow<ResultState<List<CategoryEntity>>>
}