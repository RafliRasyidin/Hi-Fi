package com.rasyidin.hi_fi.presentation.home

import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
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
                val navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_up)
                    .setExitAnim(R.anim.slide_down)
                    .setPopEnterAnim(R.anim.slide_up)
                    .setPopExitAnim(R.anim.slide_down)
                    .build()

                navController.navigate(R.id.addTransactionFragment, null, navOptions)

            }
        }
    }

}