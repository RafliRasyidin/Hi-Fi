package com.rasyidin.hi_fi.presentation.transaction.add_transaction

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.databinding.FragmentAddTransactionBinding
import com.rasyidin.hi_fi.presentation.component.FragmentBinding
import com.rasyidin.hi_fi.utils.hideBotNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTransactionFragment :
    FragmentBinding<FragmentAddTransactionBinding>(FragmentAddTransactionBinding::inflate) {

    private lateinit var pagerAdapter: TransactionPagerAdapter

    private lateinit var mediator: TabLayoutMediator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        setupViewPager()
    }

    override fun onResume() {
        super.onResume()
        hideBotNav(requireActivity())
    }

    private fun setupViewPager() {
        pagerAdapter = TransactionPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.vpTransaction.adapter = pagerAdapter

        mediator = TabLayoutMediator(binding.tabs, binding.vpTransaction) { tabs, position ->
            tabs.text = when (position) {
                0 -> getString(R.string.outcome)
                1 -> getString(R.string.income)
                else -> getString(R.string.transfer)
            }
        }
        mediator.attach()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            imgBack.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_close
                )
            )
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvTitle.text = getString(R.string.add_transaction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator.detach()
    }

}