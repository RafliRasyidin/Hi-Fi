package com.rasyidin.hi_fi.presentation.home.saldo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.databinding.FragmentBalanceBinding
import com.rasyidin.hi_fi.domain.onSuccess
import com.rasyidin.hi_fi.presentation.component.FragmentBinding
import com.rasyidin.hi_fi.utils.formatRupiah
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BalanceFragment : FragmentBinding<FragmentBalanceBinding>(FragmentBalanceBinding::inflate) {

    private val viewModel: BalanceViewModel by activityViewModels()

    private lateinit var sourceBalanceAdapter: SourcesBalanceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()

        setupAdapter()

        observeSourcesBalance()

        onClickView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSourcesBalance()
    }

    private fun observeSourcesBalance() {
        lifecycleScope.launch {
            viewModel.sourcesBalance.collect { resultState ->
                resultState.onSuccess { balances ->
                    var totalBalance = 0L
                    sourceBalanceAdapter.submitList(balances)
                    balances?.forEach { sourceBalance ->
                        totalBalance += sourceBalance.balance ?: 0
                    }
                    binding.tvTotalBalance.text = formatRupiah(totalBalance)
                }
            }
        }
    }

    private fun setupAdapter() {
        sourceBalanceAdapter = SourcesBalanceAdapter()
        binding.rvSourceBalance.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = sourceBalanceAdapter
        }
    }

    private fun setToolbar() {
        binding.toolbar.tvTitle.text = getString(R.string.total_saldo)
    }

    private fun onClickView() {
        binding.apply {
            btnAddBalance.setOnClickListener {
                findNavController().navigate(BalanceFragmentDirections.actionBalanceFragmentToAddSourceBalanceFragment())
            }

            toolbar.imgBack.setOnClickListener {
                requireActivity().finish()
            }
        }

        sourceBalanceAdapter.onItemClick = { sourceBalance ->
            findNavController().navigate(
                BalanceFragmentDirections.actionBalanceFragmentToAddSourceBalanceFragment(
                    REQUEST_UPDATE,
                    sourceBalance,
                )
            )
        }
    }

    companion object {
        const val REQUEST_UPDATE = 101
    }

}