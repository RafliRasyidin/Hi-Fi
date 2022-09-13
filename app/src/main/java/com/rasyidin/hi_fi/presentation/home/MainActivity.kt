package com.rasyidin.hi_fi.presentation.home

import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.databinding.ActivityMainBinding
import com.rasyidin.hi_fi.presentation.component.ActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ActivityBinding<ActivityMainBinding>() {

    private lateinit var navController: NavController

    override fun inflateViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        navController = Navigation.findNavController(this, R.id.nav_container)
        binding.botNavView.setupWithNavController(navController)

        onClickView()
    }

    private fun onClickView() {
        binding.apply {
            fabAdd.setOnClickListener {
                navController.navigate(R.id.addTransactionFragment)
            }
        }
    }

}