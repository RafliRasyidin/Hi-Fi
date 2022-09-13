package com.rasyidin.hi_fi.domain.model.balance

import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.ALMS
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.ATM
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.BILL
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.CASH
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.ENTERTAIN
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.ETC_INCOME
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.ETC_OUTCOME
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.FOOD
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.HEALTH
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.HOBBY
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.HOLIDAY
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.INSURANCE
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.PET
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.SHOP
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.SOCIAL
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.SPORT
import com.rasyidin.hi_fi.domain.model.balance.CategoryKey.TRANSPORT

data class Category(
    var id: Int,
    var bgColor: Int,
    var imageCategory: Int,
    var name: Int
)

fun generateOutcomeCategories(): List<Category> {
    val incomeCategories = ArrayList<Category>()
    incomeCategories.apply {
        add(Category(FOOD, R.color.bg_red, R.drawable.ic_food_n_drink, R.string.food_and_drink))
        add(Category(SHOP, R.color.bg_light_blue, R.drawable.ic_belanja, R.string.shop))
        add(Category(TRANSPORT, R.color.bg_blue, R.drawable.ic_kendaraan, R.string.transport))
        add(Category(BILL, R.color.bg_purple, R.drawable.ic_tagihan, R.string.bill))
        add(Category(HEALTH, R.color.bg_gray, R.drawable.ic_kesehatan, R.string.health))
        add(Category(HOLIDAY, R.color.bg_green, R.drawable.ic_liburan, R.string.holiday))
        add(Category(PET, R.color.bg_pink, R.drawable.ic_peliharaan, R.string.pet))
        add(Category(INSURANCE, R.color.bg_purple, R.drawable.ic_asuransi, R.string.insurance))
        add(Category(ALMS, R.color.bg_red, R.drawable.ic_sedekah, R.string.alm))
        add(Category(ENTERTAIN, R.color.bg_green, R.drawable.ic_hiburan, R.string.entertain))
        add(Category(HOBBY, R.color.bg_yellow, R.drawable.ic_hobi, R.string.hobby))
        add(Category(SPORT, R.color.bg_purple, R.drawable.ic_olahraga, R.string.sport))
        add(Category(SOCIAL, R.color.bg_yellow, R.drawable.ic_social, R.string.social))
        add(Category(ETC_OUTCOME, R.color.bg_blue, R.drawable.ic_lain_lain, R.string.etc))
    }
    return incomeCategories
}

fun generateIncomeCategories(): List<Category> {
    val outcomeCategories = ArrayList<Category>()
    outcomeCategories.apply {
        add(Category(CASH, R.color.bg_green, R.drawable.ic_cash, R.string.cash))
        add(Category(ATM, R.color.bg_blue, R.drawable.ic_atm, R.string.atm))
        add(Category(ETC_INCOME, R.color.bg_blue, R.drawable.ic_lain_lain, R.string.etc))
    }
    return outcomeCategories
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
    const val ALMS = 8
    const val ENTERTAIN = 9
    const val HOBBY = 10
    const val SPORT = 11
    const val SOCIAL = 12
    const val ETC_OUTCOME = 13
    const val CASH = 14
    const val ATM = 15
    const val ETC_INCOME = 16
}

enum class TransactionCategorize {
    OUTCOME,
    INCOME,
    TRANSFER,
}

