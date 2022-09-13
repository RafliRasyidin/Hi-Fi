package com.rasyidin.hi_fi.presentation.component

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rasyidin.hi_fi.databinding.BotSheetCategoryBinding
import com.rasyidin.hi_fi.domain.model.balance.Category
import com.rasyidin.hi_fi.domain.model.balance.generateIncomeCategories
import com.rasyidin.hi_fi.domain.model.balance.generateOutcomeCategories
import com.rasyidin.hi_fi.presentation.transaction.CategoryAdapter

class BotSheetCategoryFragment : BottomSheetDialogFragment() {

    private var _binding: BotSheetCategoryBinding? = null
    private val binding get() = _binding!!

    var onItemClickCallback: ((Category) -> Unit)? = null

    private lateinit var categoryAdapter: CategoryAdapter

    private var isShowOutcome = false
    private lateinit var categoryBotSheet: CategoryBotSheet

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

        isShowOutcome = arguments?.getBoolean(ARG_CATEGORY) ?: false
        categoryBotSheet = (arguments?.getSerializable(ARG_CATEGORY_BOT_SHEET)
            ?: CategoryBotSheet.TRANSACTION) as CategoryBotSheet
        title = arguments?.getString(ARG_TITLE) ?: ""

        if (title.isNotEmpty()) {
            binding.labelCategory.text = title
        }

        setupAdapter()

        onViewClick()
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
                val categories =
                    if (isShowOutcome) generateOutcomeCategories() else generateIncomeCategories()
                categoryAdapter.submitList(categories)
            }
            CategoryBotSheet.SOURCE_BALANCE -> {
                categoryAdapter = CategoryAdapter(false)
                val sourceBalanceCategories = generateIncomeCategories()
                categoryAdapter.submitList(sourceBalanceCategories)
            }
        }
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(requireActivity(), 3)
            adapter = categoryAdapter
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

        const val ARG_CATEGORY_BOT_SHEET = "argCategoryBotSheet"
        const val TAG = "BotSheetCategoryFragment"
        const val ARG_CATEGORY = "argCategory"
        const val ARG_TITLE = "argTitle"
    }

    enum class CategoryBotSheet {
        TRANSACTION,
        SOURCE_BALANCE
    }
}