package com.rasyidin.hi_fi.presentation.home.saldo

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.rasyidin.hi_fi.databinding.ActivityBalanceBinding
import com.rasyidin.hi_fi.presentation.component.ActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BalanceActivity : ActivityBinding<ActivityBalanceBinding>() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun inflateViewBinding(): ActivityBalanceBinding {
        return ActivityBalanceBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostBalance.id) as NavHostFragment
        navController = navHostFragment.navController
        supportActionBar?.hide()
    }
}