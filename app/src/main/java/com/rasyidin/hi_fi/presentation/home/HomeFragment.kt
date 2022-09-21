package com.rasyidin.hi_fi.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidin.hi_fi.databinding.FragmentHomeBinding
import com.rasyidin.hi_fi.domain.onSuccess
import com.rasyidin.hi_fi.presentation.component.FragmentBinding
import com.rasyidin.hi_fi.utils.formatRupiah
import com.rasyidin.hi_fi.utils.showBotNav
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : FragmentBinding<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by activityViewModels()

    private var totalBalance = 0L

    private lateinit var transactionAdapter: HistoryTransactionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapterHistoryTransaction()

        observeSourceBalance()

        observeHistoriesTransaction()

        onClickView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSourcesBalance()
        viewModel.getHistoriesTransaction()
        showBotNav(requireActivity())
    }

    private fun observeSourceBalance() {
        lifecycleScope.launch {
            viewModel.sourceBalance.collect { resultState ->
                resultState.onSuccess { sources ->
                    totalBalance = 0
                    sources?.forEach { sourceBalance ->
                        totalBalance += sourceBalance.balance ?: 0
                    }
                    binding.tvSaldo.text = formatRupiah(totalBalance)
                }
            }
        }
    }

    private fun setupAdapterHistoryTransaction() {
        transactionAdapter = HistoryTransactionAdapter()
        binding.rvRecentActivities.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = transactionAdapter
        }
    }

    private fun observeHistoriesTransaction() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.historiesTransaction.collect { resultState ->
                resultState.onSuccess { sourceBalanceAndTransaction ->
                    transactionAdapter.submitList(sourceBalanceAndTransaction)
                    binding.rvRecentActivities.smoothScrollToPosition(0)
                    Log.d("TransactionHistory", sourceBalanceAndTransaction.toString())
                    Log.d("TransactionHistory", sourceBalanceAndTransaction?.size.toString())
                }
            }
        }
    }

    private fun onClickView() {
        binding.apply {
            cardTotalSaldo.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBalanceActivity())
            }
        }
    }

}