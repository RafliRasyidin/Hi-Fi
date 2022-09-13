package com.rasyidin.hi_fi.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rasyidin.hi_fi.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSourceBalance()

        onClickView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSourcesBalance()
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

    private fun onClickView() {
        binding.apply {
            cardTotalSaldo.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBalanceActivity())
            }
        }
    }

}