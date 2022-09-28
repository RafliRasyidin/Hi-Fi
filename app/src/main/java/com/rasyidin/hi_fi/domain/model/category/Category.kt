package com.rasyidin.hi_fi.domain.model.category

import com.rasyidin.hi_fi.data.source.local.entity.CategoryEntity
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance

data class Category(
    var id: Int? = null,
    var bgColor: Int,
    var imageCategory: Int,
    var name: Int? = null,
    var nameString: String? = null,
    var typeCategory: String? = null
) {
    fun toEntity() = CategoryEntity(
        categoryId = id,
        bgColor = bgColor,
        imageCategory = imageCategory,
        name = name,
        nameString = nameString,
        typeCategory = typeCategory
    )
}

fun generateSourceBalanceExisting(data: List<SourceBalance>?): List<Category> {
    val sourceBalanceExisting = ArrayList<Category>()
    sourceBalanceExisting.apply {
        data?.forEach { sourceBalance ->
            add(
                Category(
                    id = sourceBalance.sourceId ?: 0,
                    bgColor = sourceBalance.bgColor ?: 0,
                    imageCategory = sourceBalance.iconPath ?: 0,
                    nameString = sourceBalance.name
                )
            )
        }
    }
    return sourceBalanceExisting
}

object CategoryKey {
    const val FOOD = 1
    const val SHOP = 2
    const val TRANSPORT = 3
    const val BILL = 4
    const val HEALTH = 5
    const val HOLIDAY = 6
    const val PET = 7
    const val INSURANCE = 8
    const val ALMS = 9
    const val ENTERTAIN = 10
    const val HOBBY = 11
    const val SPORT = 12
    const val SOCIAL = 13
    const val ETC_OUTCOME = 14
    const val CASH = 15
    const val ATM = 16
    const val ETC_INCOME = 17
    const val SALARY = 18
    const val BONUS = 19
    const val GIFT = 20
    const val BUSINESS = 21
    const val INVESTATION = 22
    const val SALE = 23
    const val ETC_INCOME_CATEGORY = 24
}

enum class TransactionCategorize {
    OUTCOME,
    INCOME,
    TRANSFER,
}

const val OUTCOME = "Outcome"
const val INCOME = "Income"
const val INCOME_CATEGORY = "IncomeCategory"

