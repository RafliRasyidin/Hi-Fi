package com.rasyidin.hi_fi.domain.usecase.transaction

import com.rasyidin.hi_fi.data.repository.CategoryRepository
import com.rasyidin.hi_fi.domain.ResultState
import com.rasyidin.hi_fi.domain.mapResult
import com.rasyidin.hi_fi.domain.model.category.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategoriesByType (private val repository: CategoryRepository) {

    suspend operator fun invoke(type: String): Flow<ResultState<List<Category>>> {
        return repository.getCategoriesByType(type).map { resultState ->
            mapResult(resultState) {
                this?.map { categoryEntity ->
                    categoryEntity.toDomain()
                }
            }
        }
    }
}