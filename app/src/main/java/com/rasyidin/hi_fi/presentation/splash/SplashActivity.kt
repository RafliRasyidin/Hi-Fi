package com.rasyidin.hi_fi.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rasyidin.hi_fi.domain.onSuccess
import com.rasyidin.hi_fi.presentation.home.MainActivity
import com.rasyidin.hi_fi.presentation.on_boarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    private var isOnBoarded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeOnBoardingState()

        checkSession()
    }

    private fun observeOnBoardingState() {
        viewModel.getOnBoardingState()
        lifecycleScope.launchWhenCreated {
            viewModel.onBoardingState.collect { resultState ->
                resultState.onSuccess { state ->
                    state?.let {
                        isOnBoarded = it
                    }
                }
            }
        }
    }

    private fun checkSession() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(500)
            val intent = if (isOnBoarded) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, OnBoardingActivity::class.java)
            }
            startActivity(intent)
        }
    }
}