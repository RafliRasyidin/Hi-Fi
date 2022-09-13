package com.rasyidin.hi_fi.presentation.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.databinding.FragmentReportBinding
import com.rasyidin.hi_fi.presentation.component.FragmentBinding
import com.rasyidin.hi_fi.utils.hideBotNav
import com.rasyidin.hi_fi.utils.showBotNav

class ReportFragment : FragmentBinding<FragmentReportBinding>(FragmentReportBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        showBotNav(requireActivity())
    }
}