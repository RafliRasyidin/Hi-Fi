package com.rasyidin.hi_fi.utils

import android.content.Context
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.data.source.local.entity.SourceBalanceEntity
import com.rasyidin.hi_fi.domain.model.category.*

object InitialDataSource {
    fun initSourceBalance(context: Context): List<SourceBalanceEntity> {
        return listOf(
            SourceBalanceEntity(
                0,
                context.getString(R.string.cash),
                0,
                getCurrentDate(DEFAULT_DATE_FORMAT),
                R.drawable.ic_cash,
                R.color.bg_green
            )
        )
    }

    fun generateOutcomeCategories(): List<Category> {
        val outcomeCategories = ArrayList<Category>()
        outcomeCategories.apply {
            add(Category(CategoryKey.FOOD, R.color.bg_red, R.drawable.ic_food_n_drink, R.string.food_and_drink, typeCategory = OUTCOME))
            add(Category(CategoryKey.SHOP, R.color.bg_light_blue, R.drawable.ic_belanja, R.string.shop, typeCategory = OUTCOME))
            add(Category(CategoryKey.TRANSPORT, R.color.bg_blue, R.drawable.ic_kendaraan, R.string.transport, typeCategory = OUTCOME))
            add(Category(CategoryKey.BILL, R.color.bg_purple, R.drawable.ic_tagihan, R.string.bill, typeCategory = OUTCOME))
            add(Category(CategoryKey.HEALTH, R.color.bg_gray, R.drawable.ic_kesehatan, R.string.health, typeCategory = OUTCOME))
            add(Category(CategoryKey.HOLIDAY, R.color.bg_green, R.drawable.ic_liburan, R.string.holiday, typeCategory = OUTCOME))
            add(Category(CategoryKey.PET, R.color.bg_pink, R.drawable.ic_peliharaan, R.string.pet, typeCategory = OUTCOME))
            add(Category(CategoryKey.INSURANCE, R.color.bg_purple, R.drawable.ic_asuransi, R.string.insurance, typeCategory = OUTCOME))
            add(Category(CategoryKey.ALMS, R.color.bg_red, R.drawable.ic_sedekah, R.string.alm, typeCategory = OUTCOME))
            add(Category(CategoryKey.ENTERTAIN, R.color.bg_green, R.drawable.ic_hiburan, R.string.entertain, typeCategory = OUTCOME))
            add(Category(CategoryKey.HOBBY, R.color.bg_yellow, R.drawable.ic_hobi, R.string.hobby, typeCategory = OUTCOME))
            add(Category(CategoryKey.SPORT, R.color.bg_purple, R.drawable.ic_olahraga, R.string.sport, typeCategory = OUTCOME))
            add(Category(CategoryKey.SOCIAL, R.color.bg_yellow, R.drawable.ic_social, R.string.social, typeCategory = OUTCOME))
            add(Category(CategoryKey.ETC_OUTCOME, R.color.bg_blue, R.drawable.ic_lain_lain, R.string.etc, typeCategory = OUTCOME))
            add(Category(CategoryKey.CASH, R.color.bg_green, R.drawable.ic_cash, R.string.cash, typeCategory = INCOME))
            add(Category(CategoryKey.ATM, R.color.bg_blue, R.drawable.ic_atm, R.string.atm, typeCategory = INCOME))
            add(Category(CategoryKey.ETC_INCOME, R.color.bg_blue, R.drawable.ic_lain_lain, R.string.etc, typeCategory = INCOME))
        }
        return outcomeCategories
    }

    fun generateIncomeCategories(): List<Category> {
        val incomeCategories = ArrayList<Category>()
        incomeCategories.apply {
            add(Category(CategoryKey.SALARY, R.color.bg_green, R.drawable.ic_money_bag, R.string.salary, typeCategory = INCOME_CATEGORY))
            add(Category(CategoryKey.BONUS, R.color.bg_blue, R.drawable.ic_bonus, R.string.bonus, typeCategory = INCOME_CATEGORY))
            add(Category(CategoryKey.GIFT, R.color.bg_yellow, R.drawable.ic_gift, R.string.gift, typeCategory = INCOME_CATEGORY))
            add(Category(CategoryKey.BUSINESS, R.color.bg_green, R.drawable.ic_bisnis, R.string.business, typeCategory = INCOME_CATEGORY))
            add(Category(CategoryKey.INVESTATION, R.color.bg_light_blue, R.drawable.ic_investasi_income, R.string.investation, typeCategory = INCOME_CATEGORY))
            add(Category(CategoryKey.SALE, R.color.bg_pink, R.drawable.ic_sale, R.string.sale, typeCategory = INCOME_CATEGORY))
            add(Category(CategoryKey.ETC_INCOME_CATEGORY, R.color.bg_red, R.drawable.ic_lain_lain, R.string.sale, typeCategory = INCOME_CATEGORY))
        }
        return incomeCategories
    }
}