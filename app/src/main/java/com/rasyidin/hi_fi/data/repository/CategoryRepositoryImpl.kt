package com.rasyidin.hi_fi.data.repository

import com.rasyidin.hi_fi.data.source.local.db.CategoryDao
import com.rasyidin.hi_fi.data.source.local.entity.CategoryEntity
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.fetch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val dao: CategoryDao) :
    CategoryRepository {

    override suspend fun addCategory(categoryEntity: CategoryEntity) {
        dao.addCategory(categoryEntity)
    }

    override suspend fun getCategoriesByType(type: String): Flow<ResultState<List<CategoryEntity>>> {
        return fetch {
            dao.getCategoriesByType(type)
        }
    }
}