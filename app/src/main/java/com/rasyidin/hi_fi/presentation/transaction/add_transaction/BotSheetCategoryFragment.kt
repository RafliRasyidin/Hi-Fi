package com.rasyidin.hi_fi.presentation.transaction.add_transaction

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rasyidin.hi_fi.databinding.BotSheetCategoryBinding
import com.rasyidin.hi_fi.domain.model.balance.SourceBalance
import com.rasyidin.hi_fi.domain.model.category.Category
import com.rasyidin.hi_fi.domain.model.category.INCOME
import com.rasyidin.hi_fi.domain.model.category.OUTCOME
import com.rasyidin.hi_fi.domain.model.category.generateSourceBalanceExisting
import com.rasyidin.hi_fi.domain.onSuccess
import com.rasyidin.hi_fi.presentation.transaction.CategoryAdapter
import com.rasyidin.hi_fi.presentation.transaction.TransactionViewModel
import kotlinx.coroutines.launch

class BotSheetCategoryFragment : BottomSheetDialogFragment() {

    private var _binding: BotSheetCategoryBinding? = null
    private val binding get() = _binding!!

    var onItemClickCallback: ((Category) -> Unit)? = null

    private lateinit var categoryAdapter: CategoryAdapter

    private var isShowOutcome = false
    private lateinit var categoryBotSheet: CategoryBotSheet

    private lateinit var sourceBalanceExisting: List<SourceBalance>

    private val viewModel: TransactionViewModel by activityViewModels()

    private var title = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BotSheetCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            setFullHeight()
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArguments()

        if (title.isNotEmpty()) {
            binding.labelCategory.text = title
        }

        setupAdapter()

        onViewClick()

        observeCategories()
    }

    private fun initArguments() {
        isShowOutcome = arguments?.getBoolean(ARG_CATEGORY) ?: false
        categoryBotSheet = (arguments?.getSerializable(ARG_CATEGORY_BOT_SHEET)
            ?: CategoryBotSheet.TRANSACTION) as CategoryBotSheet
        title = arguments?.getString(ARG_TITLE) ?: ""
        sourceBalanceExisting = arguments?.getParcelableArrayList(ARG_SOURCE_BALANCE) ?: emptyList()
    }

    private fun setFullHeight() {
        val metrics = DisplayMetrics()
        requireActivity().windowManager?.defaultDisplay?.getMetrics(metrics)
        binding.root.layoutParams.height = metrics.heightPixels
        binding.root.requestLayout()
    }

    private fun onViewClick() {
        binding.imgClose.setOnClickListener {
            dismiss()
        }
        categoryAdapter.onItemClickCallback = { category ->
            onItemClickCallback?.invoke(category)
            dismiss()
        }
    }

    private fun setupAdapter() {
        when (categoryBotSheet) {
            CategoryBotSheet.TRANSACTION -> {
                categoryAdapter = CategoryAdapter()
                if (isShowOutcome) {
                    viewModel.getCategoriesByType(OUTCOME)
                } else {
                    viewModel.getCategoriesByType(INCOME)
                }
            }
            CategoryBotSheet.SOURCE_BALANCE -> {
                categoryAdapter = CategoryAdapter(false)
                viewModel.getCategoriesByType(INCOME)
            }
            CategoryBotSheet.SOURCE_BALANCE_EXISTING -> {
                categoryAdapter = CategoryAdapter()
                val sourceBalanceMapped = generateSourceBalanceExisting(sourceBalanceExisting)
                categoryAdapter.submitList(sourceBalanceMapped)
            }
        }
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(requireActivity(), 3)
            adapter = categoryAdapter
        }
    }

    private fun observeCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoriesByType.collect { resultState ->
                    resultState.onSuccess { categories ->
                        categoryAdapter.submitList(categories)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(
            category: CategoryBotSheet,
            isShowOutcome: Boolean
        ): BotSheetCategoryFragment {
            val botSheet = BotSheetCategoryFragment()
            val bundle = Bundle().apply {
                putBoolean(ARG_CATEGORY, isShowOutcome)
                putSerializable(ARG_CATEGORY_BOT_SHEET, category)
            }
            botSheet.arguments = bundle
            return botSheet
        }

        @JvmStatic
        fun newInstance(category: CategoryBotSheet, title: String): BotSheetCategoryFragment {
            val botSheet = BotSheetCategoryFragment()
            val bundle = Bundle().apply {
                putSerializable(ARG_CATEGORY_BOT_SHEET, category)
                putString(ARG_TITLE, title)
            }
            botSheet.arguments = bundle
            return botSheet
        }

        @JvmStatic
        fun newInstance(
            category: CategoryBotSheet,
            sourceBalanceExisting: List<SourceBalance>
        ): BotSheetCategoryFragment {
            val botSheet = BotSheetCategoryFragment()
            val bundle = Bundle().apply {
                putSerializable(ARG_CATEGORY_BOT_SHEET, category)
                putParcelableArrayList(
                    ARG_SOURCE_BALANCE,
                    sourceBalanceExisting as ArrayList<out Parcelable>
                )
            }
            botSheet.arguments = bundle
            return botSheet
        }

        const val ARG_CATEGORY_BOT_SHEET = "argCategoryBotSheet"
        const val TAG = "BotSheetCategoryFragment"
        const val ARG_CATEGORY = "argCategory"
        const val ARG_TITLE = "argTitle"
        const val ARG_SOURCE_BALANCE = "argSourceBalance"
    }

    enum class CategoryBotSheet {
        TRANSACTION,
        SOURCE_BALANCE,
        SOURCE_BALANCE_EXISTING
    }
}