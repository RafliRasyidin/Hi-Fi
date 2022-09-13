package com.rasyidin.hi_fi.presentation.transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.todayInUtcMilliseconds
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.databinding.FragmentAddTransactionBinding
import com.rasyidin.hi_fi.domain.model.balance.TransactionCategorize
import com.rasyidin.hi_fi.domain.model.balance.TransactionCategorize.*
import com.rasyidin.hi_fi.domain.model.transaction.Transaction
import com.rasyidin.hi_fi.presentation.component.BotSheetCategoryFragment
import com.rasyidin.hi_fi.presentation.component.BotSheetCategoryFragment.CategoryBotSheet.TRANSACTION
import com.rasyidin.hi_fi.presentation.component.FragmentBinding
import com.rasyidin.hi_fi.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddTransactionFragment :
    FragmentBinding<FragmentAddTransactionBinding>(FragmentAddTransactionBinding::inflate) {

    private var isOutcome = true

    private var categoryId = 0

    private var state: TransactionCategorize = OUTCOME

    private var dateTime = getCurrentDate()

    private lateinit var botSheetCategory: BotSheetCategoryFragment

    private val viewModel: TransactionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        binding.tvSelectDate.text = getCurrentDate()

        observeTextChange()

        onClickView()
    }

    override fun onResume() {
        super.onResume()
        hideBotNav(requireActivity())
    }

    private fun onClickView() {
        binding.apply {
            btnOutcome.setOnClickListener {
                state = OUTCOME
                isOutcome = true
                changeBtnStateClick()
            }
            btnIncome.setOnClickListener {
                state = INCOME
                isOutcome = false
                changeBtnStateClick()
            }
            btnTransfer.setOnClickListener {
                state = TRANSFER
                changeBtnStateClick()
            }
            toolbar.imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnTypeTransaction.setOnClickListener {
                showBotSheetCategory()
            }
            btnTypeSource.setOnClickListener {

            }
            btnSelectDate.setOnClickListener {
                pickDate()
            }
            btnSave.setOnClickListener {
                saveTransaction()
            }
        }
    }

    private fun observeTextChange() {
        binding.etDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                binding.btnSave.isEnabled = text.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.etNominal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                binding.btnSave.isEnabled = text.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun saveTransaction() {
        val nominal = binding.etNominal.text.toString().clearFormatCurrency()
        val description = binding.etDesc.text.toString()
        val transaction = Transaction(
            nominal = nominal.toLong(),
            description = description,
            date = dateTime,
            idTypeTransaction = state,
            categoryId = categoryId
        )
        lifecycleScope.launch {
            viewModel.addTransaction(transaction)
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            imgBack.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_close
                )
            )
            tvTitle.text = getString(R.string.add_transaction)
        }
    }

    private fun showBotSheetCategory() {
        botSheetCategory = BotSheetCategoryFragment.newInstance(TRANSACTION, isOutcome)
        botSheetCategory.showNow(childFragmentManager, BotSheetCategoryFragment.TAG)
        botSheetCategory.onItemClickCallback = { category ->
            binding.apply {
                with(category) {
                    categoryId = id
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
                    tvTypeTransaction.text = getString(name)
                }
            }
        }
    }

    private fun pickDate() {
        val calendarConstraint = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(todayInUtcMilliseconds())
            .setTitleText(R.string.choose_date)
            .setCalendarConstraints(calendarConstraint.build())
            .build()

        datePicker.show(childFragmentManager, "DATE_PICKER")
        datePicker.addOnPositiveButtonClickListener { selection ->
            val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            utc.timeInMillis = selection
            dateTime = SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT, Locale.getDefault()).format(utc.time)
            binding.tvSelectDate.text = dateTime.toDateFormat(NORMAL_DATE_TIME_FORMAT)
            dateTime += " ${getCurrentTime()}"
            Log.d("AddTransaction", dateTime)
        }
    }

    private fun changeBtnStateClick() {
        when (state) {
            OUTCOME -> {
                binding.apply {
                    changeBtnState(btnOutcome, R.color.color_primary200)
                    changeBtnState(btnIncome, R.color.white)
                    changeBtnState(btnTransfer, R.color.white)
                }
            }
            INCOME -> {
                binding.apply {
                    changeBtnState(btnOutcome, R.color.white)
                    changeBtnState(btnIncome, R.color.color_primary200)
                    changeBtnState(btnTransfer, R.color.white)
                }
            }
            TRANSFER -> {
                binding.apply {
                    changeBtnState(btnOutcome, R.color.white)
                    changeBtnState(btnIncome, R.color.white)
                    changeBtnState(btnTransfer, R.color.color_primary200)
                }
            }
        }
    }

    private fun changeBtnState(button: MaterialCardView, color: Int) {
        button.setCardBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                color
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}