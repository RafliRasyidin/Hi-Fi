package com.rasyidin.hi_fi.presentation.on_boarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.rasyidin.hi_fi.databinding.ActivityOnBoardingBinding
import com.rasyidin.hi_fi.presentation.component.ActivityBinding
import com.rasyidin.hi_fi.presentation.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : ActivityBinding<ActivityOnBoardingBinding>() {

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun inflateViewBinding(): ActivityOnBoardingBinding {
        return ActivityOnBoardingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onNextClick()
    }

    private fun onNextClick() {
        binding.tvNext.setOnClickListener {
            viewModel.setOnBoardingState(true)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}