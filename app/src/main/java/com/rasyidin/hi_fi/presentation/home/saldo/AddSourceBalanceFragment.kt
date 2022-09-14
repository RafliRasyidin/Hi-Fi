package com.rasyidin.hi_fi.presentation.home.saldo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.databinding.FragmentAddSourceBalanceBinding
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.presentation.component.BotSheetCategoryFragment
import com.rasyidin.hi_fi.presentation.component.BotSheetCategoryFragment.CategoryBotSheet.SOURCE_BALANCE
import com.rasyidin.hi_fi.presentation.component.FragmentBinding
import com.rasyidin.hi_fi.presentation.home.saldo.BalanceFragment.Companion.REQUEST_UPDATE
import com.rasyidin.hi_fi.utils.DEFAULT_DATE_FORMAT
import com.rasyidin.hi_fi.utils.clearFormatCurrency
import com.rasyidin.hi_fi.utils.getCurrentDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSourceBalanceFragment :
    FragmentBinding<FragmentAddSourceBalanceBinding>(FragmentAddSourceBalanceBinding::inflate) {

    private val viewModel: BalanceViewModel by activityViewModels()

    private lateinit var botSheetCategory: BotSheetCategoryFragment

    private lateinit var sourceBalance: SourceBalance

    private val navArgs: AddSourceBalanceFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()

        setToolbar()

        observeTextChange()

        onClickView()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            tvTitle.text = getString(R.string.add_account)
            imgAddition1.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_delete
                )
            )
            imgAddition1.imageTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.color_red)
        }
    }

    private fun setView() {
        if (navArgs.requestUpdate == REQUEST_UPDATE) {
            sourceBalance = navArgs.sourceBalance!!
            binding.apply {
                toolbar.tvTitle.text = getString(R.string.edit_account)
                toolbar.imgAddition1.isVisible = true
                etNameAccount.setText(sourceBalance.name)
                etNominal.setText(sourceBalance.balance.toString())
                bgIcon.backgroundTintList =
                    ContextCompat.getColorStateList(requireActivity(), sourceBalance.bgColor!!)
                imgIncome.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        sourceBalance.iconPath!!
                    )
                )
                btnSave.isEnabled = true
            }
        } else {
            binding.apply {
                toolbar.tvTitle.text = getString(R.string.add_account)
            }
            sourceBalance = SourceBalance(
                name = getString(R.string.cash),
                iconPath = R.drawable.ic_cash,
                bgColor = R.color.bg_green
            )
        }
    }

    private fun onClickView() {
        binding.apply {
            toolbar.imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
            toolbar.imgAddition1.setOnClickListener {
                showDialogConfirmation()
            }
            btnSave.setOnClickListener {
                val sourceBalanceName = binding.etNameAccount.text.toString()
                val nominal = binding.etNominal.text.toString().clearFormatCurrency()
                sourceBalance.apply {
                    balance = nominal.toLong()
                    name = sourceBalanceName
                    updateAt = getCurrentDate(DEFAULT_DATE_FORMAT)
                }
                if (navArgs.requestUpdate == REQUEST_UPDATE) {
                    viewModel.updateSourceBalance(sourceBalance)
                } else {
                    viewModel.addSourceBalance(sourceBalance)
                }
                findNavController().popBackStack()
            }
            bgIcon.setOnClickListener {
                showBotSheetCategory()
            }
        }
    }

    private fun observeTextChange() {
        binding.etNameAccount.addTextChangedListener(object : TextWatcher {
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

    private fun showDialogConfirmation() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.delete_source_balance)
            .setMessage(R.string.desc_delete_source_balance)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                viewModel.deleteSourceBalance(sourceBalance)
                findNavController().popBackStack()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun showBotSheetCategory() {
        val title = getString(R.string.choose_icon)
        botSheetCategory = BotSheetCategoryFragment.newInstance(SOURCE_BALANCE, title)
        botSheetCategory.showNow(childFragmentManager, BotSheetCategoryFragment.TAG)
        botSheetCategory.onItemClickCallback = { category ->
            binding.apply {
                with(category) {
                    sourceBalance.bgColor = bgColor
                    sourceBalance.iconPath = imageCategory
                    bgIcon.backgroundTintList = ContextCompat.getColorStateList(
                        requireActivity(),
                        bgColor
                    )
                    imgIncome.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity(),
                            imageCategory
                        )
                    )
                }
            }
        }
    }

}