package com.rasyidin.hi_fi.presentation.transaction.add_transaction

import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.rasyidin.hi_fi.databinding.FragmentTransferBinding
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.domain.model.category.TransactionCategorize
import com.rasyidin.hi_fi.domain.model.transaction.Transaction
import com.rasyidin.hi_fi.domain.onFailure
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
class TransferFragment :
    FragmentBinding<FragmentTransferBinding>(FragmentTransferBinding::inflate) {

    private val viewModel: TransactionViewModel by viewModels()

    private var dateTime = getCurrentDate(DEFAULT_DATE_FORMAT) + " ${getCurrentTime()}"

    private lateinit var botSheetCategory: BotSheetCategoryFragment

    private var sourceBalances = listOf<SourceBalance>()

    private var sourceBalanceFrom = SourceBalance()

    private var sourceBalanceTo = SourceBalance()

    private var sourceAccountDestinationId = 0

    private var sourceAccountId = 0

    private var isPickSourceBalanceFrom = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSelectDate.text = getCurrentDate()

        viewModel.setButtonState(
            ValidateTransaction.TransactionPickState.SOURCE_BALANCE_FROM,
            true,
            TRANSFER
        )

        observeListSourceBalance()

        observeSourceBalance()

        observeNominalTextChange()

        observeButtonSave()

        onViewClick()

    }

    private fun onViewClick() {
        binding.apply {
            tvFromSource.setOnClickListener {
                isPickSourceBalanceFrom = true
                showBotSheetCategory()
            }

            tvToSource.setOnClickListener {
                isPickSourceBalanceFrom = false
                showBotSheetCategory()
            }

            btnSelectDate.setOnClickListener {
                pickDate()
            }

            btnTransfer.setOnClickListener {
                transfer()
            }
        }
    }

    private fun showBotSheetCategory() {
        botSheetCategory =
            BotSheetCategoryFragment.newInstance(
                BotSheetCategoryFragment.CategoryBotSheet.SOURCE_BALANCE_EXISTING,
                sourceBalances
            )

        botSheetCategory.showNow(childFragmentManager, BotSheetCategoryFragment.TAG)
        botSheetCategory.onItemClickCallback = { category ->
            binding.apply {
                if (isPickSourceBalanceFrom) {
                    viewModel.getSourceBalanceByIdFrom(category.id ?: 0)
                    viewModel.setButtonState(
                        ValidateTransaction.TransactionPickState.SOURCE_BALANCE_FROM,
                        true,
                        TRANSFER
                    )
                    tvFromSource.text = category.nameString
                } else {
                    viewModel.getSourceBalanceByIdTo(category.id ?: 0)
                    viewModel.setButtonState(
                        ValidateTransaction.TransactionPickState.SOURCE_BALANCE_TO,
                        true,
                        TRANSFER
                    )
                    tvToSource.text = category.nameString
                }
            }
        }
    }

    private fun observeNominalTextChange() {
        binding.etNominal.doOnTextChanged { text, _, _, _ ->
            val isNominalNotEmpty = text?.isNotEmpty() ?: false
            viewModel.setButtonState(
                ValidateTransaction.TransactionPickState.NOMINAL,
                isNominalNotEmpty,
                TRANSFER
            )
        }
    }

    private fun transfer() {
        if (sourceAccountDestinationId == sourceAccountId) {
            showLongSnackbar(binding.root, getString(R.string.error_source_account_same))
        } else {
            val nominal = binding.etNominal.text.toString().clearFormatCurrency()
            val transaction = Transaction(
                nominal = nominal.toLong(),
                description = getString(R.string.transfer),
                date = dateTime,
                idTypeTransaction = TransactionCategorize.TRANSFER,
                sourceAccountDestinationId = sourceAccountDestinationId,
                sourceAccountId = sourceAccountId
            )

            val sourceBalanceFromNominal = sourceBalanceFrom.balance?.minus(nominal.toLong())
            sourceBalanceFrom.apply {
                balance = sourceBalanceFromNominal
                updateAt = getCurrentDate()
            }

            val sourceBalanceToNominal = sourceBalanceTo.balance?.plus(nominal.toLong())
            sourceBalanceTo.apply {
                balance = sourceBalanceToNominal
                updateAt = getCurrentDate()
            }
            viewModel.addTransaction(transaction)
            viewModel.updateSourceBalance(sourceBalanceFrom)
            viewModel.updateSourceBalance(sourceBalanceTo)
            findNavController().popBackStack()
        }
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

    private fun observeButtonSave() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isValidated.collect { isValidated ->
                    Log.d("TransferButton", "Is Validated: $isValidated")
                    binding.btnTransfer.isEnabled = isValidated
                }
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
                            sourceBalanceFrom = sourceBalances.first()
                            sourceAccountId = sourceBalanceFrom.sourceId ?: 0
                            binding.tvFromSource.text = sourceBalanceFrom.name
                        }
                    }

                    result.onFailure {
                        it.printStackTrace()
                    }
                }
            }
        }
    }

    private fun observeSourceBalance() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sourceBalanceFrom.collect { result ->
                    result.onSuccess {
                        it?.let {
                            sourceBalanceFrom = it
                            sourceAccountId = sourceBalanceFrom.sourceId ?: 0
                        }
                    }
                    result.onFailure {
                        it.printStackTrace()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sourceBalanceTo.collect { result ->
                    result.onSuccess {
                        it?.let {
                            sourceBalanceTo = it
                            sourceAccountDestinationId = sourceBalanceTo.sourceId ?: 0
                        }
                    }
                    result.onFailure {
                        it.printStackTrace()
                    }
                }
            }
        }
    }

    companion object {
        private val TRANSFER = ValidateTransaction.TransactionType.TRANSFER
    }

}