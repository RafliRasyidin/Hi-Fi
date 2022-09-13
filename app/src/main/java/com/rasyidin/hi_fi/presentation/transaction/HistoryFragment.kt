package com.rasyidin.hi_fi.presentation.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.databinding.FragmentHistoryBinding
import com.rasyidin.hi_fi.presentation.component.FragmentBinding
import com.rasyidin.hi_fi.utils.hideBotNav

class HistoryFragment : FragmentBinding<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        hideBotNav(requireActivity())
    }
}