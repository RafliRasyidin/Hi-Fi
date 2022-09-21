package com.rasyidin.hi_fi.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasyidin.hi_fi.domain.model.category.Category
import com.rasyidin.hi_fi.domain.model.category.OUTCOME

@Entity("category")
data class CategoryEntity(
    @PrimaryKey
    var categoryId: Int,
    var bgColor: Int,
    var imageCategory: Int,
    var name: Int? = null,
    var nameString: String? = null,
    var typeCategory: String? = OUTCOME
) {
    fun toDomain() = Category(
        id = categoryId,
        bgColor = bgColor,
        imageCategory = imageCategory,
        name = name,
        nameString = nameString,
        typeCategory = typeCategory
    )
}