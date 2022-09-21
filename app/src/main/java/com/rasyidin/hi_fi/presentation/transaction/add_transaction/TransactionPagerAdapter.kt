package com.rasyidin.hi_fi.presentation.transaction.add_transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TransactionPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                createFragmentInstance(OutcomeFragment())
            }
            1 -> {
                createFragmentInstance(IncomeFragment())
            }
            else -> {
                createFragmentInstance(TransferFragment())
            }
        }
    }

    private fun createFragmentInstance(fragmentClass: Fragment): Fragment {
        val bundle = Bundle().apply {

        }
        fragmentClass.arguments = bundle
        return fragmentClass
    }
}