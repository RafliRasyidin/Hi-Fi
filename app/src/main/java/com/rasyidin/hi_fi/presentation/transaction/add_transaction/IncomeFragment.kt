package com.rasyidin.hi_fi.presentation.transaction.add_transaction

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.databinding.FragmentIncomeBinding
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.domain.model.category.TransactionCategorize
import com.rasyidin.hi_fi.domain.model.transaction.Transaction
import com.rasyidin.hi_fi.domain.onSuccess
import com.rasyidin.hi_fi.domain.usecase.transaction.ValidateTransaction
import com.rasyidin.hi_fi.presentation.component.FragmentBinding
import com.rasyidin.hi_fi.presentation.transaction.TransactionViewModel
import com.rasyidin.hi_fi.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class IncomeFragment : FragmentBinding<FragmentIncomeBinding>(FragmentIncomeBinding::inflate) {

    private val viewModel: TransactionViewModel by viewModels()

    private var dateTime = getCurrentDate(DEFAULT_DATE_FORMAT) + " ${getCurrentTime()}"

    private lateinit var botSheetCategory: BotSheetCategoryFragment

    private var sourceBalances = listOf<SourceBalance>()

    private var sourceBalance = SourceBalance()

    private var sourceAccountId: Int = 0

    private var categoryId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSelectDate.text = getCurrentDate()

        viewModel.setButtonState(ValidateTransaction.TransactionPickState.SOURCE_BALANCE, true)

        onViewClick()

        observeListSourceBalance()

        observeSourceBalance()

        observeNominalTextChange()

        observeButtonSave()

    }

    private fun onViewClick() {
        binding.apply {
            btnTypeTransaction.setOnClickListener {
                showBotSheetCategory(BotSheetCategoryFragment.CategoryBotSheet.TRANSACTION)
            }

            btnTypeSource.setOnClickListener {
                showBotSheetCategory(BotSheetCategoryFragment.CategoryBotSheet.SOURCE_BALANCE_EXISTING)
            }

            btnSelectDate.setOnClickListener {
                pickDate()
            }

            btnSave.setOnClickListener {
                saveTransaction()
            }

        }
    }

    private fun saveTransaction() {
        val nominal = binding.etNominal.text.toString().clearFormatCurrency()
        val description = binding.etDesc.text.toString()
        val transaction = Transaction(
            nominal = nominal.toLong(),
            description = description,
            date = dateTime,
            idTypeTransaction = TransactionCategorize.INCOME,
            categoryId = categoryId,
            sourceAccountId = sourceAccountId
        )

        val sourceBalanceNominal = sourceBalance.balance?.plus(nominal.toLong())
        sourceBalance.balance = sourceBalanceNominal
        sourceBalance.updateAt = getCurrentDate()
        viewModel.addTransaction(transaction)
        viewModel.updateSourceBalance(sourceBalance)
        findNavController().popBackStack()
    }

    private fun pickDate() {
        val calendarConstraint = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTitleText(R.string.choose_date)
            .setCalendarConstraints(calendarConstraint.build())
            .build()

        datePicker.show(childFragmentManager, "DATE_PICKER")
        datePicker.addOnPositiveButtonClickListener { selection ->
            val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            utc.timeInMillis = selection
            dateTime =
                SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()).format(utc.time)
            binding.tvSelectDate.text = dateTime.toDateFormat(NORMAL_DATE_FORMAT)
            dateTime += " ${getCurrentTime()}"
            Log.d("AddTransaction", dateTime)
        }

    }

    private fun observeNominalTextChange() {
        binding.etNominal.doOnTextChanged { text, _, _, _ ->
            val isNominalNotEmpty = text?.isNotEmpty() ?: false
            viewModel.setButtonState(
                ValidateTransaction.TransactionPickState.NOMINAL,
                isNominalNotEmpty
            )
        }
    }

    private fun observeButtonSave() {
        lifecycleScope.launch {
            viewModel.isValidated.collect { isValidated ->
                binding.btnSave.isEnabled = isValidated
            }
        }
    }

    private fun observeListSourceBalance() {
        viewModel.getSourceBalance()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sourceBalances.collect { result ->
                    result.onSuccess { data ->
                        if (data != null) {
                            sourceBalances = data
                            sourceBalance = sourceBalances.first()
                            sourceAccountId = sourceBalance.sourceId ?: 0
                            binding.apply {
                                tvTypeSource.text = sourceBalance.name
                                imgTypeSource.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        requireActivity(),
                                        sourceBalance.iconPath!!
                                    )
                                )
                                bgIconSource.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        requireActivity(),
                                        sourceBalance.bgColor!!
                                    )
                                )
                            }

                        }
                    }
                }
            }
        }
    }

    private fun observeSourceBalance() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sourceBalance.collect { result ->
                    result.onSuccess {
                        it?.let {
                            sourceBalance  = it
                        }
                    }
                }
            }
        }
    }

    private fun showBotSheetCategory(categoryBotSheet: BotSheetCategoryFragment.CategoryBotSheet) {
        val isPickIncomeCategory: Boolean
        botSheetCategory =
            if (categoryBotSheet == BotSheetCategoryFragment.CategoryBotSheet.SOURCE_BALANCE_EXISTING) {
                isPickIncomeCategory = false
                BotSheetCategoryFragment.newInstance(categoryBotSheet, sourceBalances)
            } else {
                isPickIncomeCategory = true
                BotSheetCategoryFragment.newInstance(categoryBotSheet, false)
            }
        botSheetCategory.showNow(childFragmentManager, BotSheetCategoryFragment.TAG)
        botSheetCategory.onItemClickCallback = { category ->
            binding.apply {
                with(category) {
                    if (categoryBotSheet == BotSheetCategoryFragment.CategoryBotSheet.SOURCE_BALANCE_EXISTING) {
                        sourceAccountId = id ?: 0
                        viewModel.getSourceBalanceById(sourceAccountId)
                        bgIconSource.setCardBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                bgColor
                            )
                        )
                        imgTypeSource.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireActivity(),
                                imageCategory
                            )
                        )
                        tvTypeSource.text = nameString
                    } else {
                        categoryId = id ?: 0
                        bgIcon.setCardBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                bgColor
                            )
                        )
                        imgTypeTransaction.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireActivity(),
                                imageCategory
                            )
                        )
                        tvTypeTransaction.text = getString(name ?: 0)
                    }
                }
            }
            if (isPickIncomeCategory) {
                viewModel.setButtonState(ValidateTransaction.TransactionPickState.CATEGORY, true)
            } else {
                viewModel.setButtonState(ValidateTransaction.TransactionPickState.SOURCE_BALANCE, true)
            }
        }
    }

}